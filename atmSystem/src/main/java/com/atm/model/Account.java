package com.atm.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@Builder
@Component
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private BankUser bankUser;
    private BigDecimal balance;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private List<Transaction> transactions;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }
    public Account(BankUser bankUser) {
        this.bankUser = bankUser;
    }

    public Account(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
}

