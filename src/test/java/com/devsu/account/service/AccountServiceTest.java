package com.devsu.account.service;

import com.devsu.account.dto.AccountDTO;
import com.devsu.account.entity.Account;
import com.devsu.account.repository.AccountRepository;
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
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    @Test
    void shouldCreateAccount_ReturnsDTO(){
        AccountDTO dto = AccountDTO.builder()
                .accountNumber("ACC123")
                .accountType("Savings")
                .initialBalance(BigDecimal.valueOf(500))
                .status(true)
                .client("Jose")
                .build();

        Account entity = Account.builder()
                .id(1L)
                .accountNumber(dto.getAccountNumber())
                .accountType(dto.getAccountType())
                .initialBalance(dto.getInitialBalance())
                .status(dto.getStatus())
                .client(dto.getClient())
                .build();

        when(repository.save(any())).thenReturn(entity);

        AccountDTO result = service.create(dto);

        Assertions.assertEquals("ACC123", result.getAccountNumber());
        Assertions.assertEquals("Savings", result.getAccountType());
        verify(repository).save(any());

    }

    @Test
    void shouldGetAccountByNumber() {
        Account entity = Account.builder()
                .accountNumber("ACC123")
                .accountType("Savings")
                .initialBalance(BigDecimal.valueOf(500))
                .status(true)
                .client("Jose")
                .build();

        when(repository.findByAccountNumber("ACC123")).thenReturn(Optional.of(entity));

        AccountDTO result = service.getByAccountNumber("ACC123");

        Assertions.assertEquals("Jose", result.getClient());
        Assertions.assertEquals("Savings", result.getAccountType());

    }

}
