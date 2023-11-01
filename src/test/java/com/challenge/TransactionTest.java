package com.challenge;

import com.challenge.model.Transaction;
import com.challenge.persistence.TransactionPersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
        Transaction transaction1 = new Transaction(100d, "phone");
        Transaction transaction2 = new Transaction(200d, "phone");
        Transaction transaction3 = new Transaction(300d, "pen");
        Transaction transaction4 = new Transaction(500d, "pen");

        transaction2.setParentId(1L);
        transaction3.setParentId(2L);
        transaction4.setParentId(2L);

        transactionPersistence.createTransaction(1L, transaction1);
        transactionPersistence.createTransaction(2L, transaction2);
        transactionPersistence.createTransaction(3L, transaction3);
        transactionPersistence.createTransaction(4L, transaction4);

        Optional<Long> sum1 = transactionPersistence.getTransitiveAmountSum(1L);
        Optional<Long> sum2 = transactionPersistence.getTransitiveAmountSum(2L);
        Optional<Long> sum3 = transactionPersistence.getTransitiveAmountSum(3L);

        assertEquals(sum1.get(), 1100);
        assertEquals(sum2.get(), 1000);
        assertEquals(sum3.get(), 300);
    }

}
