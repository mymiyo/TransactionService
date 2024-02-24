package ru.benyfox.TransactionsRestApi.dto.Limit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;

@Getter
@Setter
public class LimitCreateDTO {
    @Min(value = 0, message = "Limit sum should be greater than 0")
    private long limitSum;

    @NotEmpty(message = "The currency of the limit must not be empty")
    private String limitCurrencyShortname;

    @NotEmpty(message = "The account number should not be empty")
    private String accountNumber;

    private ExpenseCategory category;
}
