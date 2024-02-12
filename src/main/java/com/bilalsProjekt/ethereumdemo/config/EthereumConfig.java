package com.bilalsProjekt.ethereumdemo.config;

import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigInteger;

@Component
public class EthereumConfig {
    Dotenv dotenv = Dotenv.load();
    String privateKey = dotenv.get("ETHEREUM_PRIVATE_KEY");

    BigInteger gasLimit = BigInteger.valueOf(21_000);

    public Credentials getCredentials() {
        return Credentials.create(privateKey);
    }

    public BigInteger getGaslimit() {
        return gasLimit;
    }
}
