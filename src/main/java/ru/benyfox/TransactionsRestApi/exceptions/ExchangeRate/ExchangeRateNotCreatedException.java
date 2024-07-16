package ru.benyfox.TransactionsRestApi.exceptions.ExchangeRate;

public class ExchangeRateNotCreatedException extends RuntimeException{
    public ExchangeRateNotCreatedException(String msg) {
        super(msg);
    }
}
