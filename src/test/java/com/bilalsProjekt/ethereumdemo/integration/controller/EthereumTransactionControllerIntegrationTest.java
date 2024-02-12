package com.bilalsProjekt.ethereumdemo.integration.controller;

import com.bilalsProjekt.ethereumdemo.controller.EthereumTransactionController;
import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;
import com.bilalsProjekt.ethereumdemo.services.EthereumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EthereumTransactionController.class)
public class EthereumTransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EthereumService service;

    private EthereumTransaction transaction;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        transaction = new EthereumTransaction();
        transaction.setId(1L);
        transaction.setAddressFrom("0xFromAddress");
        transaction.setAddressTo("0xToAddress");
        transaction.setAmount(1.0); // 1 Ether
        transaction.setTransactionHash("0xTransactionHash");
    }

    @Test
    public void getAllTransactions_whenTransactionsExist_returnsAllTransactions() throws Exception {
        given(service.getAllTransactions()).willReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/getAllTransactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(transaction.getId()))
                .andExpect(jsonPath("$[0].addressFrom").value(transaction.getAddressFrom()))
                .andExpect(jsonPath("$[0].addressTo").value(transaction.getAddressTo()))
                .andExpect(jsonPath("$[0].amount").value(transaction.getAmount().toString()))
                .andExpect(jsonPath("$[0].transactionHash").value(transaction.getTransactionHash()));
    }

    @Test
    public void getTransactionById_whenTransactionExists_returnsTransaction() throws Exception {
        given(service.getTransactionById(1L)).willReturn(Optional.of(transaction));

        mockMvc.perform(get("/getTransactionById/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.addressFrom").value(transaction.getAddressFrom()))
                .andExpect(jsonPath("$.addressTo").value(transaction.getAddressTo()))
                .andExpect(jsonPath("$.amount").value(transaction.getAmount().toString()))
                .andExpect(jsonPath("$.transactionHash").value(transaction.getTransactionHash()));
    }

    @Test
    public void createTransaction_whenValidInput_createsTransaction() throws Exception {
        // Konfigurieren des Mocks, um die Transaktion zurückzugeben, wenn sendTransaction aufgerufen wird
        given(service.sendTransaction(any(EthereumTransaction.class))).willReturn(transaction);

        mockMvc.perform(post("/createTransaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.addressFrom").value(transaction.getAddressFrom()))
                .andExpect(jsonPath("$.addressTo").value(transaction.getAddressTo()))
                .andExpect(jsonPath("$.amount").value(transaction.getAmount()))
                .andExpect(jsonPath("$.transactionHash").value(transaction.getTransactionHash()));
    }
}
