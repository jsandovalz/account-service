package com.devsu.account.service;

import com.devsu.account.dto.TransactionDTO;
import com.devsu.account.entity.Account;
import com.devsu.account.entity.Transaction;
import com.devsu.account.exception.AccountNotFoundException;
import com.devsu.account.exception.InsufficientBalanceException;
import com.devsu.account.exception.TransactionNotFoundException;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final String CREDIT = "CREDIT";
    private final String DEBIT = "DEBIT";

    public TransactionDTO getById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return TransactionDTO.builder()
                .accountNumber(transaction.getAccountNumber())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .build();
    }

    public TransactionDTO register(TransactionDTO dto) {
        Account account = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(()->new AccountNotFoundException("Account not found"));
        BigDecimal newBalance = BigDecimal.ZERO;

        if(dto.getType().toUpperCase().equals(CREDIT)) {
            newBalance = account.getInitialBalance().add(dto.getAmount());
        } else if (dto.getType().toUpperCase().equals(DEBIT)) {
            newBalance = account.getInitialBalance().subtract(dto.getAmount());
        }


        if(newBalance.compareTo(BigDecimal.ZERO)<0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        Transaction transaction = Transaction.builder()
                .date(LocalDate.now())
                .type(dto.getType())
                .amount(dto.getAmount())
                .balance(newBalance)
                .accountNumber(dto.getAccountNumber())
                .build();
        transactionRepository.save(transaction);
        account.setInitialBalance(newBalance);
        accountRepository.save(account);
        return dto;
    }

    public TransactionDTO update(Long id, TransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setAccountNumber(dto.getAccountNumber());
        transactionRepository.save(transaction);
        return dto;
    }

    public void delete(Long id) {
        transactionRepository.delete(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found")));
    }


}
