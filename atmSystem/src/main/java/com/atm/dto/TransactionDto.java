package com.atm.dto;

import com.atm.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal value;
    private TransactionType transactionType;
    private Long fromIdAccount;
    private Long toIdAccount;

}
