package ru.benyfox.TransactionsRestApi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "ExchangeRate")
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
    private final double value;
}
