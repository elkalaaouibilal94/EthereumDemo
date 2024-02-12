package com.bilalsProjekt.ethereumdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.IOException;
import java.math.BigInteger;

@Service
public class Web3jBlockchainService implements Web3ServiceInterface {

    private final Web3j web3j;
    @Autowired
    public Web3jBlockchainService(Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public BigInteger getNonce(String address) throws IOException {
        return web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
                .send()
                .getTransactionCount();
    }

    @Override
    public BigInteger getGasPrice() throws IOException {
        return web3j.ethGasPrice()
                .send()
                .getGasPrice();
    }

    @Override
    public String sendTransaction(String hexValue) throws IOException {
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Error sending transaction: " + ethSendTransaction.getError().getMessage());
        }

        return ethSendTransaction.getTransactionHash();
    }
}

