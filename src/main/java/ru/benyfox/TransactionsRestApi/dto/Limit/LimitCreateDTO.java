package ru.benyfox.TransactionsRestApi.dto.Limit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;

import java.math.BigDecimal;

@Getter
@Setter
public class LimitCreateDTO {
    @Min(value = 0, message = "Limit sum should be greater than 0")
    private BigDecimal limitSum;
    @NotEmpty(message = "The currency of the limit must not be empty")
    private String limitCurrencyShortname;
    @Min(value = 0, message = "The account number should be a number")
    @NotNull
    private Integer accountNumber;
    private ExpenseCategory category;
}
