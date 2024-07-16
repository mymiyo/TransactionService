package ru.benyfox.TransactionsRestApi.dto.Transaction;

import lombok.Getter;
import lombok.Setter;
import ru.benyfox.TransactionsRestApi.dto.Limit.ExceededLimitDto;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionOverdraftDTO {
    private Integer accountFrom;
    private Integer accountTo;
    private BigDecimal sum;
    private String currencyShortname;
    private String expenseCategory;
    private String datetime;
    private ExceededLimitDto limit;
}
