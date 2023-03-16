package com.atm.service;

import com.atm.dto.AuthenticatedUserDto;
import com.atm.exception.LowBalanceException;
import com.atm.exception.UserAlreadyExistsException;
import com.atm.exception.UserNotFoundException;
import com.atm.exception.UserTransactionsNotFoundException;
import com.atm.model.Account;
import com.atm.model.BankUser;
import com.atm.model.Transaction;
import com.atm.model.enums.TransactionType;
import com.atm.repository.AccountRepository;
import com.atm.repository.BankUserRepository;
import com.atm.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BankUserRepository bankUserRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private static final String NOT_FOUND_MESSAGE = "User not found!";
    private static final String BENEFICIARY_NOT_FOUND_MESSAGE = "Beneficiary user not found!";



    public BankUser registerUser(AuthenticatedUserDto user) {
        BankUser bankUser = new BankUser();
        bankUser.setUserName(user.getUserName());
        bankUser.setPassword(user.getPassword());

        bankUserRepository.findBankUserByUserName(bankUser.getUserName())
                .ifPresent(s -> { throw new UserAlreadyExistsException("User already exists!");});

        Account account = new Account();
        account.setBankUser(bankUser);
        accountRepository.save(account);
        bankUserRepository.save(bankUser);
        return bankUser;
    }

    public BankUser loginUser(AuthenticatedUserDto bankUser) {
        return bankUserRepository.findBankUserByUserNameAndPassword(bankUser.getUserName(), bankUser.getPassword())
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Bad credentials!"));
    }

    public Account getUserDetails(Long id) {
        return accountRepository.findAccountByBankUserId(id)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public Transaction createDepositTransaction(Long id, BigDecimal amount) {
        Account bankUserAccount = accountRepository.findAccountByBankUserId(id)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE));

            Transaction transaction = createTransaction(amount, TransactionType.DEPOSIT);
            bankUserAccount.getTransactions().add(transaction);
            bankUserAccount.setBalance(bankUserAccount.getBalance().add(amount));
            accountRepository.save(bankUserAccount);

            return transaction;
    }

    @Transactional
    public Transaction createWithdrawTransaction(Long id, BigDecimal amount) {
        Account bankUserAccount = accountRepository.findAccountByBankUserId(id)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE));

        if (amount.compareTo(bankUserAccount.getBalance()) > 0)
            throw new LowBalanceException("Insufficient funds!");

        Transaction transaction = createTransaction(amount, TransactionType.WITHDRAW);
        bankUserAccount.getTransactions().add(transaction);
        bankUserAccount.setBalance(bankUserAccount.getBalance().subtract(amount));
        accountRepository.save(bankUserAccount);

        return transaction;
    }

    public Transaction createTransferTransaction(Long id1, Long id2, BigDecimal amount) {
        Account senderAccount = accountRepository.findAccountByBankUserId(id1)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE));

        Account beneficiaryAccount = accountRepository.findAccountByBankUserId(id2)
                .orElseThrow(() -> new UserNotFoundException(BENEFICIARY_NOT_FOUND_MESSAGE));

        if (amount.compareTo(senderAccount.getBalance()) > 0)
            throw new LowBalanceException("Insufficient funds!");

        return realizeTransferTransaction(amount, senderAccount, beneficiaryAccount);
    }

    @Transactional
    public Transaction realizeTransferTransaction(BigDecimal amount, Account senderAccount, Account beneficiaryAccount) {
        Transaction sendTransaction = createTransaction(amount, TransactionType.TRANSFER);
        sendTransaction.setFromIdAccount((senderAccount.getId()));
        sendTransaction.setToIdAccount((beneficiaryAccount.getId()));
        senderAccount.getTransactions().add(sendTransaction);
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        transactionRepository.save(sendTransaction);
        accountRepository.save(senderAccount);

        Transaction receiveTransaction = createTransaction(amount, TransactionType.TRANSFER);
        receiveTransaction.setFromIdAccount((senderAccount.getId()));
        receiveTransaction.setToIdAccount((beneficiaryAccount.getId()));
        beneficiaryAccount.getTransactions().add(receiveTransaction);
        beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(amount));
        transactionRepository.save(receiveTransaction);
        accountRepository.save(beneficiaryAccount);

        return sendTransaction;
    }

    public List<Transaction> getBankStatement(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        if (accountRepository.findAccountByBankUserId(id).isEmpty())
            throw new UserNotFoundException(NOT_FOUND_MESSAGE);

        List<Transaction> transactionList = transactionRepository.getUserTransactionsBetweenDates(id, startDate, endDate);
        if (transactionList.isEmpty())
            throw new UserTransactionsNotFoundException("No transactions found!");

        return transactionList;
    }

    public Transaction createTransaction(BigDecimal amount, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setValue(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return transaction;
    }

    @Transactional
    public void shotDownUserBankAccount(String id) {
        BankUser bankUser = bankUserRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE));

        Account account = accountRepository.findAccountByBankUserId(Long.parseLong(id))
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_MESSAGE));

        List<Transaction> transactions = transactionRepository.getTransactionsByAccountId(account.getId());

        bankUserRepository.delete(bankUser);
        accountRepository.delete(account);
        transactionRepository.deleteAll(transactions);
    }

}

