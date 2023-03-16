package com.atm.exception;

public class UserTransactionsNotFoundException extends RuntimeException {
    public UserTransactionsNotFoundException(String message) {
        super(message);
    }
}
