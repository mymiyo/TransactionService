package ru.benyfox.TransactionsRestApi.dto.Limit;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class LimitResponseDTO {
    private int accountNumber;

    private long limitSum;

    private OffsetDateTime offsetDateTime;

    private String limitCurrencyShortname;

    private boolean limitExceeded;
}
