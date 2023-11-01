package com.challenge.controller;

import com.challenge.model.Transaction;
import com.challenge.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    public TransactionService transactionService;

    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity createTransaction(
            @PathVariable Long transactionId,
            @Valid @RequestBody Transaction transaction
    ) {
        Optional<Transaction> createdTransaction = transactionService.createTransaction(transactionId, transaction);
        if (createdTransaction.isPresent()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transactions/types/{type}")
    public ResponseEntity<List<Long>> getTransactionsByType(@PathVariable String type) {
        List<Long> ids = transactionService.getTransactionsByType(type);
        if (ids.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ids);
    }
}
