package ru.benyfox.TransactionsRestApi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;

import java.time.OffsetDateTime;

@Entity
@Table(name = "Transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_from", length = 10)
    private String accountFrom;

    @Column(name = "account_to", length = 10)
    private String accountTo;

    @Column(name = "currency_shortname")
    private String currencyShortname;

    @Column(name = "sum")
    private long sum;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(name = "datetime")
    private OffsetDateTime datetime;
}
