package com.bilalsProjekt.ethereumdemo.services;

import com.bilalsProjekt.ethereumdemo.config.EthereumConfig;
import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;
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
public class EthereumTransactionService implements EthereumService {

    @Autowired
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
    public EthereumTransaction sendTransaction(EthereumTransaction transaction) throws Exception {
        Credentials credentials = ethereumConfig.getCredentials();

        BigInteger value = Convert.toWei(transaction.getAmount().toString(), Convert.Unit.ETHER).toBigInteger();
        BigInteger nonce = web3Service.getNonce(credentials.getAddress());
        BigInteger gasLimit = BigInteger.valueOf(21_000);
        BigInteger gasPrice = web3Service.getGasPrice();

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, transaction.getAddressTo(), value);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        String transactionHash = web3Service.sendTransaction(hexValue);
        transaction.setTransactionHash(transactionHash);

        return saveTransaction(transaction);
    }

    @Override
    public EthereumTransaction saveTransaction(EthereumTransaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public List<EthereumTransaction> getAllTransactions() {
        return repository.findAll();
    }

    @Override
    public Optional<EthereumTransaction> getTransactionById(Long id) {
        return repository.findById(id);
    }
}
