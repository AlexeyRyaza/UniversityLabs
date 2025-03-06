package com.example.lab_1.services;

//public String getRoleByUserID(String user_id, String bank_id){
//        return bankRepository.getRoleByUserID(user_id, bank_id);
//    }
import com.example.lab_1.repositories.CouchbaseUserBankRepository;

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

    public void saveRole(String userId, String bankId, String role) {
        userBankRepository.saveRole(userId, bankId, role);
    }
}

