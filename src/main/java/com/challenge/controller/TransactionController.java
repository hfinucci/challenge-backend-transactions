package com.challenge.controller;

import com.challenge.model.Transaction;
import com.challenge.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Log4j2
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    public TransactionService transactionService;

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> createTransaction(
            @PathVariable Long transactionId,
            @Valid @RequestBody Transaction transaction
    ) {
        if (transactionId < 0) {
            log.info("Invalid request: Transaction id cannot be negative");
            return ResponseEntity.badRequest().build();
        }
        if (transaction.getParentId() != null && transaction.getParentId().equals(transactionId)) {
            log.info("Invalid request: Transaction cannot be parent of itself");
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<Transaction> createdTransaction = transactionService.createTransaction(transactionId, transaction);
            if (createdTransaction.isEmpty()) {
                log.error("Error creating transaction");
                return ResponseEntity.internalServerError().build();
            }
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        log.info("Transaction created or updated successfully");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<Long>> getTransactionsByType(@PathVariable String type) {
        List<Long> ids = transactionService.getTransactionsByType(type);
        if (ids.isEmpty()) {
            log.info("No transactions found for type: " + type);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ids);
    }

    @GetMapping("/sum/{transactionId}")
    public ResponseEntity<Map<String, Long>> getTransitiveAmountSum(@PathVariable Long transactionId) {
        Optional<Long> sum = transactionService.getTransitiveAmountSum(transactionId);
        Map<String, Long> response = new HashMap<>();
        if (sum.isEmpty()) {
            log.info("No children transactions found for id: " + transactionId);
            return ResponseEntity.notFound().build();
        }
        response.put("sum", sum.get());
        return ResponseEntity.ok(response);
    }
}
