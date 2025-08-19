package com.devsu.account.service;

import com.devsu.account.dto.AccountDTO;
import com.devsu.account.dto.ReportDTO;
import com.devsu.account.dto.TransactionDTO;
import com.devsu.account.entity.Account;
import com.devsu.account.entity.Transaction;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ReportDTO generate(String client, LocalDate from, LocalDate to) {
        List<Account> accounts = accountRepository.findByClient(client);
        List<AccountDTO> accountDTOS = accounts.stream()
                .map(a->
                        AccountDTO.builder()
                                .accountNumber(a.getAccountNumber())
                                .accountType(a.getAccountType())
                                .initialBalance(a.getInitialBalance())
                                .status(a.getStatus())
                                .client(a.getClient())
                                .build()
                ).toList();


        return ReportDTO.builder()
                .client(client)
                .accounts(accountDTOS)
                .transactionHistory(getTransactionsMap(accountDTOS,from,to))
                .build();
    }

    private Map<String, List<TransactionDTO>> getTransactionsMap(List<AccountDTO> accounts,LocalDate from, LocalDate to) {
        Map<String, List<TransactionDTO>> transactionsMap = new HashMap<>();
        for(AccountDTO account : accounts) {
            List<Transaction> transactions = transactionRepository
                    .findByAccountNumberAndDateBetween(account.getAccountNumber(),from,to);
            List<TransactionDTO> transactionDTOS = transactions.stream()
                    .map(m->
                            TransactionDTO.builder()
                                    .accountNumber(m.getAccountNumber())
                                    .type(m.getType())
                                    .amount(m.getAmount())
                                    .balance(m.getBalance())
                                    .build()
                    ).toList();
            transactionsMap.put(account.getAccountNumber(),transactionDTOS);
        }
        return transactionsMap;
    }
}
