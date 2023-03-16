package com.atm.service;

import com.atm.exception.UserNotFoundException;
import com.atm.model.Account;
import com.atm.model.BankUser;
import com.atm.model.Transaction;
import com.atm.model.enums.TransactionType;
import com.atm.repository.AccountRepository;
import com.atm.repository.BankUserRepository;
import com.atm.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock
    private BankUserRepository bankUserRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankService bankService;


    @Test
    public void getAllUsers() {
        BankUser user1 = new BankUser(1L, "Mahmut", "test1@gmail.com");
        BankUser user2 = new BankUser(2L, "Ali", "test2@gmail.com");
        List<BankUser> users = Arrays.asList(user1, user2);

        when(bankUserRepository.findAll()).thenReturn(users);
        List<BankUser> result = bankService.getAllUsers();

        assertEquals(users, result);
    }


    @Test
    void getAllUsersAccount() {
        List<Account> expectedAccounts = new ArrayList<>();
        expectedAccounts.add(new Account(1L, new BigDecimal("100.00")));
        expectedAccounts.add(new Account(2L, new BigDecimal("500.00")));
        expectedAccounts.add(new Account(3L, new BigDecimal("1000.00")));

        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        Account account = new Account();
        List<Account> actualAccounts = bankService.getAllUsersAccount();

        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void getAllTransactions() {
        List<Transaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(new Transaction(1L, LocalDateTime.now(), new BigDecimal("100.00"), TransactionType.DEPOSIT, 1L, 2L, new Account(1L, new BigDecimal("100.00"))));
        expectedTransactions.add(new Transaction(2L, LocalDateTime.now(), new BigDecimal("50.00"), TransactionType.WITHDRAW, 3L, 1L, new Account(2L, new BigDecimal("500.00"))));
        expectedTransactions.add(new Transaction(3L, LocalDateTime.now(), new BigDecimal("200.00"), TransactionType.TRANSFER, 2L, 3L, new Account(3L, new BigDecimal("1000.00"))));

        when(transactionRepository.findAll()).thenReturn(expectedTransactions);

        Transaction transaction = new Transaction();
        List<Transaction> actualTransactions = bankService.getAllTransactions();

        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void getHighestAccountBalance() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L, new BigDecimal("1000.00")));
        accounts.add(new Account(2L, new BigDecimal("500.00")));
        accounts.add(new Account(3L, new BigDecimal("2000.00")));

        when(accountRepository.findUserWithHighestBalance()).thenReturn(accounts.get(2));

        Account account = new Account();
        Account highestBalanceAccount = bankService.getHighestAccountBalance();

        assertEquals(accounts.get(2), highestBalanceAccount);

    }

    @Test
    void calculateBankBalance() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L, new BigDecimal("1000.00")));
        accounts.add(new Account(2L, new BigDecimal("500.00")));
        accounts.add(new Account(3L, new BigDecimal("2000.00")));

        when(accountRepository.findAll()).thenReturn(accounts);

        Account account = new Account();
        BigDecimal bankBalance = bankService.calculateBankBalance();

        assertEquals(new BigDecimal("3500.00"), bankBalance);

    }

    @Test
    void findUserWithMostTransactions() {
        BankUser user1 = new BankUser(1L, "Mahmut Sahinkaya", "testpassword1");
        BankUser user2 = new BankUser(2L, "Mahmut Ali", "testpassword2");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, LocalDateTime.now(), new BigDecimal("100.00"), TransactionType.DEPOSIT, 1L, 2L, new Account(1L, new BigDecimal("100.00"))));
        transactions.add(new Transaction(2L, LocalDateTime.now(), new BigDecimal("50.00"), TransactionType.WITHDRAW, 1L, 2L, new Account(2L, new BigDecimal("500.00"))));
        transactions.add(new Transaction(3L, LocalDateTime.now(), new BigDecimal("75.00"), TransactionType.DEPOSIT, 2L, 1L, new Account(3L, new BigDecimal("1000.00"))));

        when(transactionRepository.getUserIdWithMostTransactions()).thenReturn(1L);
        when(bankUserRepository.findBankUserById(1L)).thenReturn(Optional.of(user1));

        Account account = new Account();
        BankUser userWithMostTransactions = bankService.findUserWithMostTransactions();

        assertEquals(user1, userWithMostTransactions);
    }

    @Test
    void testFindUserWithMostTransactionsThrowsException() {
        when(transactionRepository.getUserIdWithMostTransactions()).thenReturn(null);

        Account account = new Account();
        assertThrows(UserNotFoundException.class, () -> bankService.findUserWithMostTransactions());

    }

    @Test
    void findDateWithMostTransactions() {
        Transaction transaction1 = new Transaction(null, LocalDateTime.of(2022, 10, 1, 10, 30), new BigDecimal("100"), TransactionType.DEPOSIT, null, 1L, new Account(1L, new BigDecimal("100.00")));
        Transaction transaction2 = new Transaction(null, LocalDateTime.of(2022, 10, 1, 12, 30), new BigDecimal("200"), TransactionType.WITHDRAW, 1L, null, new Account(2L, new BigDecimal("500.00")));
        Transaction transaction3 = new Transaction(null, LocalDateTime.of(2022, 10, 2, 10, 30), new BigDecimal("300"), TransactionType.DEPOSIT, null, 1L, new Account(3L, new BigDecimal("1000.00")));
        Transaction transaction4 = new Transaction(null, LocalDateTime.of(2022, 10, 2, 12, 30), new BigDecimal("400"), TransactionType.WITHDRAW, 1L, null, new Account(1L, new BigDecimal("100.00")));
        Transaction transaction5 = new Transaction(null, LocalDateTime.of(2022, 10, 3, 10, 30), new BigDecimal("500"), TransactionType.DEPOSIT, null, 1L, new Account(2L, new BigDecimal("500.00")));
        Transaction transaction6 = new Transaction(null, LocalDateTime.of(2022, 10, 3, 12, 30), new BigDecimal("600"), TransactionType.WITHDRAW, 1L, null, new Account(3L, new BigDecimal("1000.00")));
        transactionRepository.saveAll(List.of(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6));
        LocalDateTime expectedDateTime = LocalDateTime.of(2022, 10, 3, 10, 30);
        Date expectedDate = Date.from(expectedDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Date result = transactionRepository.getDateWithMostTransactions();

        if (result == null) {
            assertNull(result);
        } else {
            LocalDateTime resultDateTime = result.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            assertEquals(expectedDateTime, resultDateTime);
        }
    }

    @Test
    public void testFindDateWithMostTransactions() {
        // Arrange
        Date expectedDate = new Date();
        when(transactionRepository.getDateWithMostTransactions()).thenReturn(expectedDate);

        // Act
        Date actualDate = bankService.findDateWithMostTransactions();

        // Assert
        assertEquals(expectedDate, actualDate);
    }












    @Test
    void getTransactionsBetweenDate() {
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 31, 23, 59, 59);
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setTimestamp(LocalDateTime.of(2022, 3, 15, 10, 30, 0));
        transaction1.setValue(new BigDecimal(100));
        transaction1.setTransactionType(TransactionType.DEPOSIT);
        transaction1.setFromIdAccount(null);
        transaction1.setToIdAccount(1L);
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setTimestamp(LocalDateTime.of(2022, 5, 20, 14, 0, 0));
        transaction2.setValue(new BigDecimal(50));
        transaction2.setTransactionType(TransactionType.WITHDRAW);
        transaction2.setFromIdAccount(2L);
        transaction2.setToIdAccount(null);
        transactions.add(transaction2);
        when(transactionRepository.getBankTransactionsBetweenDates(startDate, endDate)).thenReturn(transactions);

        List<Transaction> result = bankService.getTransactionsBetweenDate(startDate, endDate);

        assertEquals(2, result.size());
        assertEquals(transaction1, result.get(0));
        assertEquals(transaction2, result.get(1));
    }
}