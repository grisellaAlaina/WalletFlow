package org.example.walletflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientFundsException extends WalletException {
    public InsufficientFundsException(long balance, long requiredAmount) {
        super("INSUFFICIENT_FUNDS", "Insufficient funds. Balance: " + balance + ", Required: " + requiredAmount);
    }
}