package ru.benyfox.TransactionsRestApi.exceptions.Limit;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LimitErrorResponse {
    private String message;
    private long timestamp;
}
