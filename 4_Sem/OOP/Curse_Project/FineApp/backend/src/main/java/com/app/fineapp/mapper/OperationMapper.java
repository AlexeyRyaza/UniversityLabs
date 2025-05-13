package com.app.fineapp.mapper;

import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.model.Account;
import com.app.fineapp.model.Category;
import com.app.fineapp.model.Operation;

public class OperationMapper {

    public static OperationDTO toDTO(Operation operation) {
        OperationDTO dto = new OperationDTO();
        dto.setId(operation.getId());
        dto.setAccountId(operation.getAccount() != null ? operation.getAccount().getId() : null);
        dto.setCategoryId(operation.getCategory() != null ? operation.getCategory().getId() : null);
        dto.setAmount(operation.getAmount());
        dto.setComment(operation.getComment());
        dto.setArchived(operation.isArchived());
        dto.setDate(operation.getDate());
        dto.setImage(operation.getImage());
        dto.setColor(operation.getColor());
        return dto;
    }

    public static Operation toEntity(OperationDTO dto, Account account, Category category) {
        Operation operation = new Operation();
        operation.setId(dto.getId());
        operation.setAccount(account);
        operation.setCategory(category);
        operation.setAmount(dto.getAmount());
        operation.setComment(dto.getComment());
        operation.setArchived(dto.isArchived());
        operation.setDate(dto.getDate());
        operation.setImage(dto.getImage());
        operation.setColor(dto.getColor());
        return operation;
    }
}
