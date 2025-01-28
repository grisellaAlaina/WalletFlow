package org.example.walletflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.walletflow.dto.WalletDTO;
import org.example.walletflow.exception.InsufficientFundsException;
import org.example.walletflow.exception.WalletNotFoundException;
import org.example.walletflow.model.Wallet;
import org.example.walletflow.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUpdateBalance_Success() throws Exception {
        // Arrange
        WalletDTO request = new WalletDTO();
        request.setWalletId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        request.setAmount(1000L);
        request.setOperationType("DEPOSIT");

        Wallet wallet = new Wallet();
        wallet.setId(request.getWalletId());
        wallet.setBalance(1000L);

        when(walletService.updateBalance(any(UUID.class), any(Long.class), any(String.class)))
                .thenReturn(wallet);

        // Act & Assert
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getWalletId().toString()))
                .andExpect(jsonPath("$.balance").value(1000L));
    }

    @Test
    public void testGetBalance_Success() throws Exception {
        UUID walletId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        when(walletService.getBalance(walletId)).thenReturn(1000L);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isOk())
                .andExpect(content().string("1000"));
    }

    @Test
    public void testUpdateBalance_InsufficientFunds() throws Exception {
        // Arrange
        WalletDTO request = new WalletDTO();
        request.setWalletId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        request.setAmount(2000L);
        request.setOperationType("WITHDRAW");

        when(walletService.updateBalance(any(UUID.class), any(Long.class), any(String.class)))
                .thenThrow(new InsufficientFundsException(1000L, 2000L));

        // Act & Assert
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INSUFFICIENT_FUNDS"))
                .andExpect(jsonPath("$.message").value("Insufficient funds. Balance: 1000, Required: 2000"));
    }


    @Test
    public void testGetBalance_WalletNotFound() throws Exception {
        // Arrange
        UUID walletId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        when(walletService.getBalance(walletId))
                .thenThrow(new WalletNotFoundException(walletId));

        // Act & Assert
        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("WALLET_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Wallet not found: " + walletId));
    }



}