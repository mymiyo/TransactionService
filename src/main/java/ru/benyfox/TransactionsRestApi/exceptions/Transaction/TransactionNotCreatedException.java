package ru.benyfox.TransactionsRestApi.exceptions.Transaction;

public class TransactionNotCreatedException extends RuntimeException{
    public TransactionNotCreatedException(String msg) {
        super(msg);
    }
}
