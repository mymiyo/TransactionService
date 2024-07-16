package ru.benyfox.TransactionsRestApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.benyfox.TransactionsRestApi.dto.Limit.LimitCreateDTO;
import ru.benyfox.TransactionsRestApi.dto.Limit.LimitResponseDTO;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionErrorResponse;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotCreatedException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotFoundException;
import ru.benyfox.TransactionsRestApi.services.LimitService;

import java.util.List;

@RestController
@RequestMapping("/limits")
@AllArgsConstructor
public class LimitsController {
    private final LimitService limitService;

    @GetMapping("/category/{category}")
    public List<LimitResponseDTO> getLimitsByCategory(@PathVariable ExpenseCategory category) {
        return limitService.getLimitsList(category);
    }

    @GetMapping("/{id}")
    public LimitResponseDTO getLimit(@PathVariable int id) {
        return limitService.findOneById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveLimit(@RequestBody @Valid LimitCreateDTO limitCreateDTO,
                                                      BindingResult bindingResult) {
        limitService.save(limitCreateDTO, bindingResult);
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

    @ExceptionHandler
    private ResponseEntity<TransactionErrorResponse> handleException(MethodArgumentTypeMismatchException exception) {
        TransactionErrorResponse response = new TransactionErrorResponse(
                "Invalid argument",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
