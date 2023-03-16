package com.atm.dto;

import com.atm.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BasicTransactionDto {
    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal value;
    private TransactionType transactionType;

}
