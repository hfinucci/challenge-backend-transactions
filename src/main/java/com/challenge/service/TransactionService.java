package com.challenge.service;

import com.challenge.model.Transaction;
import com.challenge.persistence.TransactionPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionService {

    @Autowired
    public TransactionPersistence transactionPersistence;

    public Optional<Transaction> createTransaction(Long transactionId, Transaction transaction) {
        return transactionPersistence.createTransaction(transactionId, transaction);
    }

    public List<Long> getTransactionsByType(String type) {
        return transactionPersistence.getTransactionsByType(type);
    }

    public Optional<Long> getTransitiveAmountSum(Long transactionId) {
        return transactionPersistence.getTransitiveAmountSum(transactionId);
    }
}
