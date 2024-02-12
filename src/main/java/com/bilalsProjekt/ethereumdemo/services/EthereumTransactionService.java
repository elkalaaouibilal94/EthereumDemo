package com.bilalsProjekt.ethereumdemo.services;

import com.bilalsProjekt.ethereumdemo.config.EthereumConfig;
import com.bilalsProjekt.ethereumdemo.model.EthereumTransactionModel;
import com.bilalsProjekt.ethereumdemo.repository.EthereumTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class EthereumTransactionService implements EthereumTransactionServiceInterface {

    private final EthereumTransactionRepository repository;
    private final Web3ServiceInterface web3Service;
    private final EthereumConfig ethereumConfig;

    @Autowired
    public EthereumTransactionService(EthereumTransactionRepository repository,
                                      Web3ServiceInterface web3Service,
                                      EthereumConfig ethereumConfig) {
        this.repository = repository;
        this.web3Service = web3Service;
        this.ethereumConfig = ethereumConfig;
    }

    @Override
    public EthereumTransactionModel sendTransaction(EthereumTransactionModel transaction) throws Exception {
        Credentials credentials = ethereumConfig.getCredentials();

        BigInteger value = Convert.toWei(transaction.getAmount().toString(), Convert.Unit.ETHER).toBigInteger();
        BigInteger nonce = web3Service.getNonce(credentials.getAddress());
        BigInteger gasLimit = ethereumConfig.getGaslimit();
        BigInteger gasPrice = web3Service.getGasPrice();

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, transaction.getAddressTo(), value);
        String hexValue = signMessage(rawTransaction, credentials);

        String transactionHash = web3Service.sendTransaction(hexValue);
        transaction.setTransactionHash(transactionHash);

        return saveTransaction(transaction);
    }

    @Override
    public EthereumTransactionModel saveTransaction(EthereumTransactionModel transaction) {
        return repository.save(transaction);
    }

    @Override
    public String signMessage(RawTransaction rawTransaction, Credentials credentials) {
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        return Numeric.toHexString(signedMessage);
    }

    @Override
    public List<EthereumTransactionModel> getAllTransactions() {
        return repository.findAll();
    }

    @Override
    public Optional<EthereumTransactionModel> getTransactionById(Long id) {
        return repository.findById(id);
    }
}
