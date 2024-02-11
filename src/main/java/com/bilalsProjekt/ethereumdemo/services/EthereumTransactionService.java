package com.bilalsProjekt.ethereumdemo.services;

import com.bilalsProjekt.ethereumdemo.config.EthereumConfig;
import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;
import com.bilalsProjekt.ethereumdemo.repository.EthereumTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class EthereumTransactionService implements EthereumService {
    private final EthereumTransactionRepository repository;
    private final Web3j web3j;
    private final EthereumConfig ethereumConfig;

    @Autowired
    public EthereumTransactionService(EthereumTransactionRepository repository, EthereumConfig ethereumConfig) {
        this.repository = repository;
        this.ethereumConfig = ethereumConfig;
        this.web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/31b85f686ec84fc685bb652cc05508b2"));
    }

    @Override
    public EthereumTransaction sendTransaction(EthereumTransaction transaction) throws Exception {
        Credentials credentials = ethereumConfig.getCredentials();

        BigInteger value = Convert.toWei(transaction.getAmount().toString(), Convert.Unit.ETHER).toBigInteger();
        BigInteger nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
        BigInteger gasLimit = BigInteger.valueOf(21_000);
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, transaction.getAddressTo(), value);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Fehler beim Senden der Transaktion: " + ethSendTransaction.getError().getMessage());
        }

        transaction.setTransactionHash(ethSendTransaction.getTransactionHash());
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
