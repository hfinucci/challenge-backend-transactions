package com.challenge.controller;

import com.challenge.model.Transaction;
import com.challenge.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Transaction createdTransaction = transactionService.createTransaction(transactionId, transaction);
        if (createdTransaction == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
