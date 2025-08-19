package com.devsu.account.service;

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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldRegisterTransaction() {
        Account account= Account.builder()
                .accountNumber("ACC123")
                .initialBalance(BigDecimal.valueOf(1000))
                .build();
        TransactionDTO dto = TransactionDTO.builder()
                .accountNumber("ACC123")
                .type("Deposit")
                .amount(BigDecimal.valueOf(200))
                .build();
        when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account));
        when(transactionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(accountRepository.save(any())).thenReturn(account);

        TransactionDTO result = service.register(dto);

        Assertions.assertEquals("Deposit",result.getType());
        Assertions.assertEquals(BigDecimal.valueOf(200),result.getAmount());
        verify(accountRepository).save(account);
    }

}
