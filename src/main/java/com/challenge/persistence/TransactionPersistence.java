package com.challenge.persistence;

import com.challenge.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionPersistence {

    public Transaction createTransaction(Long transactionId, Transaction transaction) {
        return transaction;
    }

    public List<Transaction> getTransactionsByType(String type) {
        return null;
    }
}