package com.bilalsProjekt.ethereumdemo.config;

import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigInteger;

@Component
public class EthereumConfig {
    BigInteger gasLimit = BigInteger.valueOf(21_000);

    public BigInteger getGaslimit() {
        return gasLimit;
    }
}
