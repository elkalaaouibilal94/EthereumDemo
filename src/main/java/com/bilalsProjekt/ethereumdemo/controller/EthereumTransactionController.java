package com.bilalsProjekt.ethereumdemo.controller;

import com.bilalsProjekt.ethereumdemo.model.EthereumTransactionModel;
import com.bilalsProjekt.ethereumdemo.services.EthereumTransactionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EthereumTransactionController {

    private final EthereumTransactionServiceInterface service;

    @Autowired
    public EthereumTransactionController(EthereumTransactionServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<EthereumTransactionModel>> getAllTransactions() {
        try {
            List<EthereumTransactionModel> txList = service.getAllTransactions();
            if (txList.isEmpty()) {
                return new ResponseEntity<>(txList, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(txList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTransactionById/{id}")
    public ResponseEntity<EthereumTransactionModel> getTransactionById(@PathVariable Long id) {
        return service.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/createTransaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EthereumTransactionModel> createTransaction(@RequestBody EthereumTransactionModel ethereumTX) {
        try {
            EthereumTransactionModel savedTransaction = service.sendTransaction(ethereumTX);
            System.out.println(savedTransaction.toString());
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
