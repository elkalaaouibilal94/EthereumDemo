package com.bilalsProjekt.ethereumdemo.services;

import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;

import java.util.List;
import java.util.Optional;

public interface EthereumService {
    EthereumTransaction sendTransaction(EthereumTransaction transaction) throws Exception;
    EthereumTransaction saveTransaction(EthereumTransaction transaction) throws Exception;
    List<EthereumTransaction> getAllTransactions();
    Optional<EthereumTransaction> getTransactionById(Long id);
}

