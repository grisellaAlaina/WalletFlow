package org.example.walletflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class WalletFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletFlowApplication.class, args);
    }

}
