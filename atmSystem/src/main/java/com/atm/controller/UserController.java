package com.atm.controller;

import com.atm.dto.AccountDto;
import com.atm.dto.BasicTransactionDto;
import com.atm.dto.TransactionDto;
import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getUserDetails(@PathVariable String id) {
        Account account = userService.getUserDetails(Long.parseLong(id));

        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @PatchMapping("/deposit")
    public ResponseEntity<BasicTransactionDto> createUserDeposit(
            @RequestParam String id,
            @RequestParam String amount) {
        Transaction transaction = userService.createDepositTransaction(Long.parseLong(id), new BigDecimal(amount));

        BasicTransactionDto basicTransactionDto = modelMapper.map(transaction, BasicTransactionDto.class);

        return new ResponseEntity<>(basicTransactionDto, HttpStatus.CREATED);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<BasicTransactionDto> createUserWithdraw(
            @RequestParam String id,
            @RequestParam String amount) {
        Transaction transaction = userService.createWithdrawTransaction(Long.parseLong(id), new BigDecimal(amount));

        BasicTransactionDto basicTransactionDto = modelMapper.map(transaction, BasicTransactionDto.class);

        return new ResponseEntity<>(basicTransactionDto, HttpStatus.CREATED);
    }

    @PatchMapping("/transfer")
    public ResponseEntity<TransactionDto> createUserTransfer(
            @RequestParam String idAccount1,
            @RequestParam String idAccount2,
            @RequestParam String amount) {
        Transaction transaction = userService.createTransferTransaction(Long.parseLong(idAccount1),
                Long.parseLong(idAccount2), new BigDecimal(amount));

        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);

        return new ResponseEntity<>(transactionDto, HttpStatus.CREATED);
    }

    @GetMapping("/statement")
    public ResponseEntity<List<TransactionDto>> getBankStatementFromAPeriod(
            @RequestParam String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<Transaction> transactions = userService.getBankStatement(Long.parseLong(id), start.atStartOfDay(), end.atStartOfDay());

        List<TransactionDto> transactionDtoList = transactions.stream().
                map(tr -> modelMapper.map(tr, TransactionDto.class))
                .toList();

        return new ResponseEntity<>(transactionDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/close-account/{id}")
    public ResponseEntity<String> closeBankAccount(@PathVariable String id) {
        userService.shotDownUserBankAccount(id);

        return new ResponseEntity<>("Bank account closed", HttpStatus.OK);
    }
}

