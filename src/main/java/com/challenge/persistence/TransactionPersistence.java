package com.challenge.persistence;

import com.challenge.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransactionPersistence {

    private final Map<Long, Transaction> transactionMap = new HashMap<>();

    public Optional<Transaction> createTransaction(Long transactionId, Transaction transaction) {
        if (transaction.getParentId() != null && transactionMap.get(transaction.getParentId()) == null) {
            throw new IllegalArgumentException("Parent transaction not found");
        }
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
    public Optional<Long> getTransitiveAmountSum(Long transactionId) {
        if (transactionMap.get(transactionId) == null) {
            return Optional.empty();
        }
        LinkedHashSet<Long> transitiveChildrenIds = new LinkedHashSet<>();
        for (Map.Entry<Long, Transaction> entry : transactionMap.entrySet()) {
            Long currentTransactionId = entry.getKey();
            Long parentId = entry.getValue().getParentId();
            LinkedHashSet<Long> possibleTransactionIds = new LinkedHashSet<>();
            while (parentId != null) {
                possibleTransactionIds.add(currentTransactionId);
                if (parentId.equals(transactionId)) {
                    transitiveChildrenIds.addAll(possibleTransactionIds);
                }
                parentId = transactionMap.get(parentId).getParentId();
            }
        }
        transitiveChildrenIds.add(transactionId);
        return transitiveChildrenIds.stream().map(id -> transactionMap.get(id).getAmount().longValue()).reduce(Long::sum);
    }

}