package org.example.walletflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WalletNotFoundException extends WalletException {
    public WalletNotFoundException(UUID walletId) {
        super("WALLET_NOT_FOUND", "Wallet not found: " + walletId);
    }
}
