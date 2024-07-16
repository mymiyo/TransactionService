package ru.benyfox.TransactionsRestApi.exceptions.ExchangeRate;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateErrorResponse {
    private String message;
    private long timestamp;
}
