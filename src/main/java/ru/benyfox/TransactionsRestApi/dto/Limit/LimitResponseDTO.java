package ru.benyfox.TransactionsRestApi.dto.Limit;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class LimitResponseDTO {
    private Integer accountNumber;
    private BigDecimal limitSum;
    private OffsetDateTime offsetDateTime;
    private String limitCurrencyShortname;
    private boolean limitExceeded;
}
