package com.app.fineapp.repository;

import com.app.fineapp.model.Account;
import com.app.fineapp.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
    List<Operation> findByAccount(Account account);

    List<Operation> findByAccountAndArchivedFalse(Account account);

    List<Operation> findByAccountAndDateBetween(Account account,
                                                LocalDateTime start,
                                                LocalDateTime end);

    List<Operation> findByAccountIn(List<Account> accounts);

    // или JPQL
    @Query("""
            select o
            from Operation o
            where o.account in :accounts
           """)
    List<Operation> findByUserAccounts(@Param("accounts") List<Account> accounts);
}
