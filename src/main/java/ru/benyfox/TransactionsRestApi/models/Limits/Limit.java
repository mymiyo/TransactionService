package ru.benyfox.TransactionsRestApi.models.Limits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;
import ru.benyfox.TransactionsRestApi.models.Transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Limits")
@Getter
@Setter
@NoArgsConstructor
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "account_number")
    private Integer accountNumber;

    @Column(name = "limit_sum")
    private BigDecimal limitSum;

    @Column(name = "limit_datetime")
    private OffsetDateTime limitDatetime;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    @Column(name = "limit_currency_shortname")
    private String limitCurrencyShortname;

    @Enumerated
    @Column(name = "expense_category")
    private ExpenseCategory expenseCategory;

    @OneToMany(mappedBy = "limit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "limit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Transaction> exceededTransactions = new LinkedHashSet<>();
}
