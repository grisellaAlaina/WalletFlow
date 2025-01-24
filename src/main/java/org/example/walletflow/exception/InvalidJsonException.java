package org.example.walletflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJsonException extends WalletException {
    public InvalidJsonException(String operationType) {
        super("INVALID_JSON", "Invalid json type: " + operationType);
    }
}