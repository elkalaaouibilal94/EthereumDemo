package com.bilalsProjekt.ethereumdemo.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "EthereumTransactions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class EthereumTransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String addressFrom;

    @NotNull
    private String addressTo;

    @NotNull
    private Double amount;

    private String transactionHash;
}
