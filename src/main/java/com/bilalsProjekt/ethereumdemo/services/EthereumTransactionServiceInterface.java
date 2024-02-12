package com.bilalsProjekt.ethereumdemo.services;

import com.bilalsProjekt.ethereumdemo.model.EthereumTransactionModel;

import java.util.List;
import java.util.Optional;

public interface EthereumTransactionServiceInterface {
    EthereumTransactionModel sendTransaction(EthereumTransactionModel transaction) throws Exception;

    EthereumTransactionModel saveTransaction(EthereumTransactionModel transaction) throws Exception;

    List<EthereumTransactionModel> getAllTransactions();

    Optional<EthereumTransactionModel> getTransactionById(Long id);
}

