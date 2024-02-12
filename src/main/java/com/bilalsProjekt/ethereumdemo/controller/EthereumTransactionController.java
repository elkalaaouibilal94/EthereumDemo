package com.bilalsProjekt.ethereumdemo.controller;

import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;
import com.bilalsProjekt.ethereumdemo.services.EthereumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EthereumTransactionController {

    private final EthereumService service;

    @Autowired
    public EthereumTransactionController(EthereumService service) {
        this.service = service;
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<EthereumTransaction>> getAllTransactions() {
        try {
            List<EthereumTransaction> txList = service.getAllTransactions();
            if (txList.isEmpty()) {
                return new ResponseEntity<>(txList, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(txList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTransactionById/{id}")
    public ResponseEntity<EthereumTransaction> getTransactionById(@PathVariable Long id) {
        return service.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/createTransaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EthereumTransaction> createTransaction(@RequestBody EthereumTransaction ethereumTX) {
        try {
            EthereumTransaction savedTransaction = service.sendTransaction(ethereumTX);
            System.out.println(savedTransaction.toString());
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
