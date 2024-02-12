package com.bilalsProjekt.ethereumdemo.services;

import com.bilalsProjekt.ethereumdemo.model.EthereumTransactionModel;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;

import java.util.List;
import java.util.Optional;

public interface EthereumTransactionServiceInterface {
    EthereumTransactionModel sendTransaction(EthereumTransactionModel transaction) throws Exception;

    EthereumTransactionModel saveTransaction(EthereumTransactionModel transaction) throws Exception;

    String signMessage(RawTransaction rawTransaction, Credentials credentials);

    List<EthereumTransactionModel> getAllTransactions();

    Optional<EthereumTransactionModel> getTransactionById(Long id);
}

