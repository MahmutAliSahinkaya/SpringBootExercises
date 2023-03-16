package com.atm.service;

import com.atm.dto.AuthenticatedUserDto;
import com.atm.exception.LowBalanceException;
import com.atm.exception.UserNotFoundException;
import com.atm.model.Account;
import com.atm.model.BankUser;
import com.atm.repository.AccountRepository;
import com.atm.repository.BankUserRepository;
import com.atm.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private BankUserRepository bankUserRepository;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser() {
        // Given
        AuthenticatedUserDto userDto = new AuthenticatedUserDto();
        userDto.setUserName("Mahmut");
        userDto.setPassword("password");

        BankUser bankUser = BankUser.builder()
                .id(1L)
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();

        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.ZERO)
                .bankUser(bankUser)
                .build();

        when(bankUserRepository.findBankUserByUserName(anyString()))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);
        when(bankUserRepository.save(any(BankUser.class)))
                .thenReturn(bankUser);

        // When
        BankUser registeredUser = userService.registerUser(userDto);

        // Then
        assertNotNull(registeredUser);
        assertEquals(bankUser.getUserName(), registeredUser.getUserName());
        assertEquals(bankUser.getPassword(), registeredUser.getPassword());
    }


    @Test
    void loginUser() {
        // prepare
        AuthenticatedUserDto bankUser = new AuthenticatedUserDto("Mahmut", "pass123");

        BankUserRepository mockBankUserRepository = mock(BankUserRepository.class);
        BankUser expectedBankUser = new BankUser(1L, "Mahmut", "password123");
        List<BankUser> list = new ArrayList<>(List.of(expectedBankUser));
        when(mockBankUserRepository.findBankUserByUserNameAndPassword(bankUser.getUserName(), bankUser.getPassword()))
                .thenReturn(list.stream().findFirst());

        UserService userService = new UserService(mockBankUserRepository, null, null);

        // execute
        BankUser actualBankUser = userService.loginUser(bankUser);

        // verify
        assertNotNull(actualBankUser);
        assertEquals(expectedBankUser, actualBankUser);

    }

    @Test
    public void getUserDetails_withExistingUser_shouldReturnAccount() {
        // Arrange
        Long userId = 1L;

        BankUser user = BankUser.builder()
                .id(userId)
                .userName("testuser")
                .password("testpassword")
                .build();

        Account account = Account.builder()
                .id(1L)
                .bankUser(user)
                .balance(BigDecimal.ZERO)
                .build();

        when(accountRepository.findAccountByBankUserId(userId)).thenReturn(Optional.of(account));

        // Act
        Account result = userService.getUserDetails(userId);

        // Assert
        Assertions.assertNotNull(result);
        assertEquals(account, result);
    }

    @Test
    public void getUserDetails_withNonExistingUser_shouldThrowException() {
        // Arrange
        Long userId = 1L;

        when(accountRepository.findAccountByBankUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetails(userId));
    }





    @Test
    public void testCreateDepositTransactionWithInvalidAccountId() {
        // Test with invalid account ID
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.createDepositTransaction(999L, BigDecimal.valueOf(100)));
    }


    @Test
    public void createDepositTransaction_withNonExistingUser_shouldThrowException() {
        // Arrange
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("100.0");

        when(accountRepository.findAccountByBankUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.createDepositTransaction(userId, amount));
    }


    @Test
    void createWithdrawTransaction_whenBalanceIsNotEnough() {
        // Arrange

        UserService userService = new UserService(bankUserRepository, accountRepository, transactionRepository);
        Account bankUserAccount = new Account(1L, new BigDecimal("1000.00"));
        BigDecimal amount = new BigDecimal("1500.00");

        when(accountRepository.findAccountByBankUserId(1L)).thenReturn(Optional.of(bankUserAccount));

        // Act and Assert
        assertThrows(LowBalanceException.class, () -> userService.createWithdrawTransaction(1L, amount));
    }

    @Test
    void createWithdrawTransaction_whenAccountNotFound() {
        // Arrange

        UserService userService = new UserService(bankUserRepository, accountRepository, transactionRepository);
        BigDecimal amount = new BigDecimal("500.00");

        when(accountRepository.findAccountByBankUserId(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.createWithdrawTransaction(1L, amount));
    }


    @Test
    void createTransferTransaction_whenBeneficiaryNotFound() {
        BigDecimal amount = new BigDecimal("500.00");
        Account senderAccount = new Account(1L, new BigDecimal("1000.00"));

        when(accountRepository.findAccountByBankUserId(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findAccountByBankUserId(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.createTransferTransaction(1L, 2L, amount));
    }


    @Test
    void createTransferTransaction_whenBalanceIsNotEnough() {
        BigDecimal amount = new BigDecimal("1500.00");
        Account senderAccount = new Account(1L, new BigDecimal("1000.00"));
        Account beneficiaryAccount = new Account(2L, new BigDecimal("1000.00"));

        when(accountRepository.findAccountByBankUserId(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findAccountByBankUserId(2L)).thenReturn(Optional.of(beneficiaryAccount));

        assertThrows(LowBalanceException.class,
                () -> userService.createTransferTransaction(1L, 2L, amount));
    }


}


