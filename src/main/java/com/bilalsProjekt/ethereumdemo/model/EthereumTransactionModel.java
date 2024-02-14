package com.bilalsProjekt.ethereumdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @NotNull
    private String privateKey;

    private String transactionHash;

    @NotNull
    @JsonIgnore // Ignore privateKey field when serializing the object to JSON (sending data to client)
    public String getPrivateKey() {
        return privateKey;
    }

    @JsonProperty // Allow privateKey field to be set when deserializing JSON (receiving data from client)
    public void setPrivateKey(@NotNull String privateKey) {
        this.privateKey = privateKey;
    }
}
