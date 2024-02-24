package ru.benyfox.TransactionsRestApi.exceptions.Limit;

public class LimitNotCreatedException extends RuntimeException{
    public LimitNotCreatedException(String msg) {
        super(msg);
    }
}
