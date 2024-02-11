package com.bilalsProjekt.ethereumdemo.repository;

import com.bilalsProjekt.ethereumdemo.model.EthereumTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EthereumTransactionRepository extends JpaRepository<EthereumTransaction, Long> {
}
