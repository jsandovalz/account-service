package com.devsu.account.service;

import com.devsu.account.dto.AccountDTO;
import com.devsu.account.dto.ReportDTO;
import com.devsu.account.dto.TransactionDTO;
import com.devsu.account.entity.Account;
import com.devsu.account.entity.Transaction;
import com.devsu.account.repository.AccountRepository;
import com.devsu.account.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ReportService service;

    @Test
    void shouldGenerateReportWithAccountsAndMovements() {
        Account account = Account.builder()
                .accountNumber("ACC123")
                .accountType("Savings")
                .initialBalance(BigDecimal.valueOf(1000))
                .status(true)
                .client("Jose")
                .build();

        Transaction transaction = Transaction.builder()
                .accountNumber("ACC123")
                .type("Deposit")
                .amount(BigDecimal.valueOf(200))
                .build();

        when(accountRepository.findByClient("Jose")).thenReturn(List.of(account));
        when(transactionRepository.findByAccountNumberAndDateBetween(eq("ACC123"), any(), any()))
                .thenReturn(List.of(transaction));

        ReportDTO report = service.generate("Jose", LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 8, 16));

        Assertions.assertEquals("Jose", report.getClient());
        Assertions.assertEquals(1, report.getAccounts().size());
        Assertions.assertTrue(report.getTransactionHistory().containsKey("ACC123"));
        Assertions.assertEquals(1, report.getTransactionHistory().get("ACC123").size());
    }

}
