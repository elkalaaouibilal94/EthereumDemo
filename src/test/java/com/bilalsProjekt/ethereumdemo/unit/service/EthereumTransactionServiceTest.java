package com.bilalsProjekt.ethereumdemo.unit.service;

import com.bilalsProjekt.ethereumdemo.config.EthereumConfig;
import com.bilalsProjekt.ethereumdemo.model.EthereumTransactionModel;
import com.bilalsProjekt.ethereumdemo.repository.EthereumTransactionRepository;
import com.bilalsProjekt.ethereumdemo.services.EthereumTransactionService;
import com.bilalsProjekt.ethereumdemo.services.Web3ServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EthereumTransactionServiceTest {

    @Mock
    private EthereumTransactionRepository repository;

    @Mock
    private Web3ServiceInterface web3Service;

    @Mock
    private EthereumConfig ethereumConfig;

    @InjectMocks
    private EthereumTransactionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendTransaction_success() throws Exception {

        // Arrange
        EthereumTransactionModel transaction = new EthereumTransactionModel();
        transaction.setAmount(1.0); // Set the amount directly on the model
        transaction.setAddressTo("0xRecipientAddress");
        transaction.setPrivateKey("0x123456789abcdef"); // Set the private key directly on the model

        Credentials credentials = Credentials.create(transaction.getPrivateKey()); // Use the private key from the transaction model
        when(web3Service.getNonce(credentials.getAddress())).thenReturn(BigInteger.ONE);
        when(web3Service.getGasPrice()).thenReturn(BigInteger.valueOf(1000));
        when(ethereumConfig.getGaslimit()).thenReturn(BigInteger.valueOf(21000)); // Mocked gas limit value

        when(web3Service.sendTransaction(anyString())).thenReturn("0xTransactionHash");

        when(repository.save(any(EthereumTransactionModel.class))).thenReturn(transaction);

        // Act
        EthereumTransactionModel result = service.sendTransaction(transaction);

        // Assert
        assertNotNull(result.getTransactionHash());
        assertEquals("0xTransactionHash", result.getTransactionHash());
        verify(repository).save(transaction);
    }

}
