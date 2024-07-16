package ru.benyfox.TransactionsRestApi.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "Transaction")
@NoArgsConstructor
@Data
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_from", length = 10)
    private Integer accountFrom;

    @Column(name = "account_to", length = 10)
    private Integer accountTo;

    @Column(name = "currency_shortname")
    private String currencyShortname;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(name = "datetime")
    private OffsetDateTime datetime;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "limit_id")
    private Limit limit;
}
