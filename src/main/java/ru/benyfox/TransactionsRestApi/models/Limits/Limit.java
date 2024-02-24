package ru.benyfox.TransactionsRestApi.models.Limits;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "limit_sum")
    private long limitSum;

    @Column(name = "limit_datetime")
    private OffsetDateTime limitDatetime;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    @Column(name = "limit_currency_shortname")
    private String limitCurrencyShortname;
}
