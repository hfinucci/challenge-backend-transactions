package com.challenge.persistence;

import com.challenge.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransactionPersistence {

    private final Map<Long, Transaction> transactionMap = new HashMap<>();

    public Optional<Transaction> createTransaction(Long transactionId, Transaction transaction) {
        transaction.setId(transactionId);
        transactionMap.put(transactionId, transaction);
        return Optional.of(transaction);
    }

    public List<Long> getTransactionsByType(String type) {
        List<Long> transactionsId = new ArrayList<>();
        for (Transaction transaction : transactionMap.values()) {
            if (transaction.getType().equals(type)) {
                transactionsId.add(transaction.getId());
            }
        }
        return transactionsId;
    }

    public Long getTransitiveSum(long l) {
        return null;
    }
}