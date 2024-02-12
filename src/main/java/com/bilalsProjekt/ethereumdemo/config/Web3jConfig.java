package com.bilalsProjekt.ethereumdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Bean
    public Web3j web3j() {
        // Replace the URL with the appropriate Ethereum node URL you are using
        return Web3j.build(new HttpService("https://sepolia.infura.io/v3/31b85f686ec84fc685bb652cc05508b2"));
    }
}
