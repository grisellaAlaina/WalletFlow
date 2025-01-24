package org.example.walletflow.service;

import jakarta.transaction.Transactional;
import org.example.walletflow.exception.InsufficientFundsException;
import org.example.walletflow.exception.InvalidJsonException;
import org.example.walletflow.exception.WalletException;
import org.example.walletflow.exception.WalletNotFoundException;
import org.example.walletflow.model.Wallet;
import org.example.walletflow.repository.WalletRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            noRetryFor = WalletException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public Wallet updateBalance(UUID walletId, long amount, String operationType) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        if ("DEPOSIT".equals(operationType)) {
            wallet.setBalance(wallet.getBalance() + amount);
        } else if ("WITHDRAW".equals(operationType)) {
            if (wallet.getBalance() < amount) {
                throw new InsufficientFundsException(wallet.getBalance(), amount);
            }
            wallet.setBalance(wallet.getBalance() - amount);
        } else {
            throw new InvalidJsonException(operationType);
        }

        walletRepository.save(wallet);
        return wallet;
    }

    public long getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return wallet.getBalance();
    }
}
