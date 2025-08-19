package com.devsu.account.repository;

import com.devsu.account.dto.TransactionDTO;
import com.devsu.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumberAndDateBetween(String accountNumber, LocalDate from, LocalDate to);
}
