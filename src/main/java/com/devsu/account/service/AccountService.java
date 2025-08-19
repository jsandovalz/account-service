package com.devsu.account.service;

import com.devsu.account.dto.AccountDTO;
import com.devsu.account.entity.Account;
import com.devsu.account.exception.AccountNotFoundException;
import com.devsu.account.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public List<AccountDTO> getAll() {
        return repository.findAll()
                .stream()
                .map((account)->AccountDTO.builder()
                        .accountNumber(account.getAccountNumber())
                        .accountType(account.getAccountType())
                        .initialBalance(account.getInitialBalance())
                        .status(account.getStatus())
                        .client(account.getClient())
                        .build())
                .toList();
    }

    public AccountDTO getByAccountNumber(String number) {
        Account account = repository.findByAccountNumber(number)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .initialBalance(account.getInitialBalance())
                .status(account.getStatus())
                .client(account.getClient())
                .build();
    }


    public AccountDTO create(AccountDTO dto) {
        Account account = Account.builder()
                .accountNumber(dto.getAccountNumber())
                .accountType(dto.getAccountType())
                .status(dto.getStatus())
                .client(dto.getClient())
                .initialBalance(dto.getInitialBalance())
                .build();
        repository.save(account);
        return dto;
    }

    public AccountDTO update(String accountNumber, AccountDTO dto) {
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setAccountType(dto.getAccountType());
        account.setInitialBalance(dto.getInitialBalance());
        account.setStatus(dto.getStatus());
        account.setClient(dto.getClient());

        repository.save(account);
        return dto;
    }

    public void delete(String accountNumber) {
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        repository.delete(account);
    }

}
