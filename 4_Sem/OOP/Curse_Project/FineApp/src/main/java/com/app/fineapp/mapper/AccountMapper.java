package com.app.fineapp.mapper;

import com.app.fineapp.dto.AccountDTO;
import com.app.fineapp.model.Account;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setTitle(account.getTitle());
        dto.setDescription(account.getDescription());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setIncludeToBalance(account.isIncludeToBalance());
        dto.setImage(account.getImage()); // новые поля
        dto.setColor(account.getColor());
        return dto;
    }

    public static Account toEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setTitle(dto.getTitle());
        account.setDescription(dto.getDescription());
        account.setType(dto.getType());
        account.setBalance(dto.getBalance());
        account.setIncludeToBalance(dto.isIncludeToBalance());
        account.setImage(dto.getImage()); // новые поля
        account.setColor(dto.getColor());
        return account;
    }
}
