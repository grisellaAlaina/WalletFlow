package org.example.walletflow.controller;

import org.example.walletflow.dto.WalletDTO;
import org.example.walletflow.model.Wallet;
import org.example.walletflow.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<Wallet> updateBalance(@RequestBody WalletDTO request) {
        return ResponseEntity.ok(walletService.updateBalance(request.getWalletId(), request.getAmount(), request.getOperationType()));
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<Long> getBalance(@PathVariable UUID walletId) {
        long balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}