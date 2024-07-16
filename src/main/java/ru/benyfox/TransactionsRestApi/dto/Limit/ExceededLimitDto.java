package ru.benyfox.TransactionsRestApi.dto.Limit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceededLimitDto implements Serializable {
    BigDecimal limitSum;
    OffsetDateTime limitDatetime;
    String limitCurrencyShortname;
}