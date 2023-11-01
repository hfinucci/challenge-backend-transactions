package com.challenge;

import com.challenge.model.Transaction;
import com.challenge.persistence.TransactionPersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionTest {

    @Autowired
    private TransactionPersistence transactionPersistence;

    @Test
    public void createTransactionsAndReturnByType() {
        Transaction transaction1 = new Transaction(100d, "car");
        Transaction transaction2 = new Transaction(200d, "car");
        Transaction transaction3 = new Transaction(300d, "laptop");

        transactionPersistence.createTransaction(1L, transaction1);
        transactionPersistence.createTransaction(2L, transaction2);
        transactionPersistence.createTransaction(3L, transaction3);

        List<Long> transactionList1 = transactionPersistence.getTransactionsByType("car");
        List<Long> transactionList2 = transactionPersistence.getTransactionsByType("laptop");

        assertEquals(transactionList1, List.of(1L,2L));
        assertEquals(transactionList2, List.of(3L));
    }

    @Test
    public void createTransactionsAndReturnSumOfTransitiveChildren() {
        Transaction transaction1 = new Transaction(100d, "car");
        Transaction transaction2 = new Transaction(200d, "car");
        Transaction transaction3 = new Transaction(300d, "laptop");

        transaction2.setParentId(1L);
        transaction3.setParentId(2L);

        transactionPersistence.createTransaction(1L, transaction1);
        transactionPersistence.createTransaction(2L, transaction2);
        transactionPersistence.createTransaction(3L, transaction3);

        Long sum1 = transactionPersistence.getTransitiveSum(1L);
        Long sum2 = transactionPersistence.getTransitiveSum(2L);

        assertEquals(sum1, 600);
        assertEquals(sum2, 300);
    }

}
