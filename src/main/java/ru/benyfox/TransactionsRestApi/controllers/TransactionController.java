package ru.benyfox.TransactionsRestApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.benyfox.TransactionsRestApi.dto.Transaction.TransactionDTO;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionErrorResponse;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotCreatedException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotFoundException;
import ru.benyfox.TransactionsRestApi.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionDTO> getTransactions() {
        return transactionService.getTransactionsList();
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransaction(@PathVariable int id) {
        return transactionService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveTransaction(@RequestBody @Valid TransactionDTO transactionDTO,
                                                      BindingResult bindingResult) {
        transactionService.save(transactionDTO, bindingResult);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<TransactionErrorResponse> handleException(TransactionNotCreatedException exception) {
        TransactionErrorResponse response = new TransactionErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<TransactionErrorResponse> handleException(TransactionNotFoundException exception) {
        TransactionErrorResponse response = new TransactionErrorResponse(
                "Transaction was not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
