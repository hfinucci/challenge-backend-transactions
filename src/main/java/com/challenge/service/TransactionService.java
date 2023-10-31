package com.challenge.service;

import com.challenge.model.Transaction;
import com.challenge.persistence.TransactionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {

    @Autowired
    public TransactionPersistence transactionPersistence;

    public Transaction createTransaction(Long transactionId, Transaction transaction) {
        return transactionPersistence.createTransaction(transactionId, transaction);
    }
}
