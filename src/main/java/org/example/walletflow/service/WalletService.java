package org.example.walletflow.service;

import org.example.walletflow.model.Wallet;
import org.example.walletflow.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void updateBalance(UUID walletId, long amount, String operationType) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if ("DEPOSIT".equals(operationType)) {
            wallet.setBalance(wallet.getBalance() + amount);
        } else if ("WITHDRAW".equals(operationType)) {
            if (wallet.getBalance() < amount) {
                throw new RuntimeException("Insufficient funds");
            }
            wallet.setBalance(wallet.getBalance() - amount);
        } else {
            throw new RuntimeException("Invalid operation type");
        }
        walletRepository.save(wallet);
    }

    public long getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }
}
