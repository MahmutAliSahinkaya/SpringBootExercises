package com.atm.service;

import com.atm.exception.UserNotFoundException;
import com.atm.model.Account;
import com.atm.model.BankUser;
import com.atm.model.Transaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.BankUserRepository;
import com.atm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {
    private final AccountRepository accountRepository;
    private final BankUserRepository bankUserRepository;
    private final TransactionRepository transactionRepository;


    public List<BankUser> getAllUsers() {
        return bankUserRepository.findAll();
    }

    public List<Account> getAllUsersAccount() {
        return accountRepository.findAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Account getHighestAccountBalance() {
        return accountRepository.findUserWithHighestBalance();
    }

    public BigDecimal calculateBankBalance() {
        return accountRepository.findAll()
                .stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BankUser findUserWithMostTransactions() {
        return bankUserRepository.findBankUserById(transactionRepository.getUserIdWithMostTransactions())
                .orElseThrow(() ->new UserNotFoundException("User not found!"));
    }

    public Date findDateWithMostTransactions() {
        return transactionRepository.getDateWithMostTransactions();
    }

    public List<Transaction> getTransactionsBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getBankTransactionsBetweenDates(startDate, endDate);
    }
}

