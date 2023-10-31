package com.challenge.controller;

import com.challenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class TransactionController {

    @Autowired
    public TransactionService transactionService;

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/transactions")
    public boolean createTransaction() {
        return transactionService.createTransaction();
    }
}
