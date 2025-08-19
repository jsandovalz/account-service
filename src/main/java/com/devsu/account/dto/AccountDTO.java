package com.devsu.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Boolean status;
    private String client;

}
