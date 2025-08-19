package com.devsu.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreatedEvent {
    private Long clientId;
    private String name;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
}
