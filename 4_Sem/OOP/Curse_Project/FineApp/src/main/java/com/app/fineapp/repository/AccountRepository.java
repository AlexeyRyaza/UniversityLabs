package com.app.fineapp.repository;

import com.app.fineapp.model.Account;
import com.app.fineapp.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByType(AccountType type);
}

