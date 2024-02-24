package ru.benyfox.TransactionsRestApi.exceptions.Transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TransactionErrorResponse {
    private String message;
    private long timestamp;
}
