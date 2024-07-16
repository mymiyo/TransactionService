package ru.benyfox.TransactionsRestApi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table("exchange_rates")
@Getter
@Setter
@AllArgsConstructor
public class ExchangeRate {
    @PrimaryKey
    private UUID id;

    @Column(name = "pair")
    private final String pair;

    @Column(name = "datetime")
    private final LocalDate datetime;

    @Column(name = "value")
    private final BigDecimal value;
}
