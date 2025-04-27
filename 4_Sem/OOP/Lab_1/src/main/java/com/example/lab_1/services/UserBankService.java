package com.example.lab_1.services;

import com.example.lab_1.entities.Account;
import com.example.lab_1.entities.Credit;
import com.example.lab_1.entities.LogEntry;
import com.example.lab_1.entities.User;
import com.example.lab_1.repositories.CouchbaseUserBankRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserBankService {
    private static UserBankService instance;
    private final CouchbaseUserBankRepository userBankRepository;

    private UserBankService() {
        this.userBankRepository = new CouchbaseUserBankRepository();
    }

    public static synchronized UserBankService getInstance() {
        if (instance == null) {
            instance = new UserBankService();
        }
        return instance;
    }

    public String getUserRoleByID(String userId, String bankId) {
        return userBankRepository.getUserRoleByID(userId, bankId);
    }

    public void saveRole(String userId, String bankId, String role, boolean IsApproved) {
        userBankRepository.saveRole(userId, bankId, role, IsApproved);
    }

    public boolean isApproved(String userId, String bankId) {
        return userBankRepository.IsApproved(userId, bankId);
    }

    public List<User> getPendingUsers(String bankId) {
        List<Integer> userIds = userBankRepository.getPendingUsersIds(bankId);
        List<User> users = new ArrayList<>();

        for (Integer userId : userIds) {
            users.add(UserService.getInstance().getUserById(String.valueOf(userId)).get());
        }

        return users;
    }

    public void approveUser(String userId, String bankId) {
        userBankRepository.approveUser(userId, bankId);

        LogService.logAction(new LogEntry(
                "user_registration",
                userId + "," + bankId,
                "Пользователь " + userId + " зарегистрирован в банке: " + bankId
        ));
    }

    public void rejectUser(String userId, String bankId) {
        userBankRepository.rejectUser(userId, bankId);
    }

    /*
        + accounts
        + credits
        salaryProject
        user_Bank
     */
    public boolean deleteUser(String userId, String bankId) {
        AtomicBoolean deletedSuccessfully = new AtomicBoolean(true);

        List<Account> userAccounts = AccountService.getInstance().getAccountsByBankId(userId, bankId);
        userAccounts.forEach(account -> {
            if(!AccountService.getInstance().deleteAccount(String.valueOf(account.getAccountId()))){
                deletedSuccessfully.set(false);
            }
        });

        List<Credit> userCredits = CreditService.getInstance().getCreditsByUserId(userId);

        userCredits.forEach(credit -> {
            if(!CreditService.getInstance().deleteCredit(String.valueOf(credit.getId()))){
                deletedSuccessfully.set(false);
            }
        });

        if(!deletedSuccessfully.get()){
            return false;
        }
        if(!UserService.getInstance().deleteUserFromSalary(userId)){
            return false;
        }

        return userBankRepository.deleteUser(userId, bankId);
    }
}

