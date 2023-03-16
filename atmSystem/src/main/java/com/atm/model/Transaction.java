package com.atm.model;


import com.atm.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @Column(name = "transaction_value")
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Long fromIdAccount;
    private Long toIdAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;


    public Transaction(long id, LocalDateTime timestamp, TransactionType transactionType, BigDecimal value, Account account) {
        this.id = id;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.value = value;
        this.account = account;

    }

    public Transaction(Account account, TransactionType transactionType, BigDecimal value) {
        this.account = account;
        this.transactionType = transactionType;
        this.value = value;
    }
}
