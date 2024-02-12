package com.bilalsProjekt.ethereumdemo.services;

import java.io.IOException;
import java.math.BigInteger;

public interface Web3ServiceInterface {
    BigInteger getNonce(String address) throws IOException;

    BigInteger getGasPrice() throws IOException;

    String sendTransaction(String hexValue) throws IOException;
}
