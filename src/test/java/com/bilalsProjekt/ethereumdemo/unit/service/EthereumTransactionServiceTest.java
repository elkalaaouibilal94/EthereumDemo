package com.bilalsProjekt.ethereumdemo.unit.service;

import com.bilalsProjekt.ethereumdemo.config.EthereumConfig;
import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;
import com.bilalsProjekt.ethereumdemo.repository.EthereumTransactionRepository;
import com.bilalsProjekt.ethereumdemo.services.EthereumTransactionService;
import com.bilalsProjekt.ethereumdemo.services.Web3ServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.web3j.crypto.Credentials;

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
        EthereumTransaction transaction = new EthereumTransaction();
        transaction.setAmount(1.0);
        transaction.setAddressTo("0xRecipientAddress");

        Credentials credentials = Credentials.create("0x123564345345"); // Mocked credentials
        when(ethereumConfig.getCredentials()).thenReturn(credentials);
        when(web3Service.getNonce(anyString())).thenReturn(BigInteger.ONE);
        when(web3Service.getGasPrice()).thenReturn(BigInteger.valueOf(1000));
        when(web3Service.sendTransaction(anyString())).thenReturn("0xTransactionHash");

        when(repository.save(any(EthereumTransaction.class))).thenReturn(transaction);

        // Act
        EthereumTransaction result = service.sendTransaction(transaction);

        // Assert
        assertNotNull(result.getTransactionHash());
        assertEquals("0xTransactionHash", result.getTransactionHash());
        verify(repository).save(transaction);
    }

}
