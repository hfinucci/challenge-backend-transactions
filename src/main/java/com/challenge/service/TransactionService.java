package com.challenge.service;

import com.challenge.persistence.TransactionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {

    @Autowired
    public TransactionPersistence transactionPersistence;

    public boolean createTransaction() {
        return transactionPersistence.createTransaction();
    }
}
