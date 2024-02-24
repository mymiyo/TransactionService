package ru.benyfox.TransactionsRestApi.dto.Transaction;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;

@Getter
@Setter
public class TransactionDTO {
    @Max(value = 9999999999L, message = "The customers account must be in the format of an integer 10 digit number")
    @Min(value = 1, message = "The customers account must be in the format of an integer 10 digit number") // Другое сообщение не придумал
    private int accountFrom;

    @Max(value = 9999999999L, message = "The counterparts account must be in the format of an integer 10 digit number")
    @Min(value = 1, message = "The counterparts account must be in the format of an integer 10 digit number") // Другое сообщение не придумал
    private int accountTo;

    @NotEmpty(message = "The currency of the account must not be empty")
    private String currencyShortname;

    @Min(value = 0, message = "Sum should be greater than 0")
    private long sum;

    @NotNull(message = "The expense category should not be null")
    private ExpenseCategory expenseCategory;
}
