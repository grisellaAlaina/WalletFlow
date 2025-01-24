package org.example.walletflow.controller;

import org.example.walletflow.dto.WalletDTO;
import org.example.walletflow.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public void updateBalance(@RequestBody WalletDTO request) {
        walletService.updateBalance(request.getWalletId(), request.getAmount(), request.getOperationType());
    }

    @GetMapping("/{walletId}")
    public long getBalance(@PathVariable UUID walletId) {
        return walletService.getBalance(walletId);
    }
}