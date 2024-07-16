package ru.benyfox.TransactionsRestApi.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;
import ru.benyfox.TransactionsRestApi.models.Transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("""
        SELECT SUM(t.sum)
         FROM Transaction t
          WHERE t.accountFrom = ?1 AND t.datetime > ?2 AND t.expenseCategory = ?3
    """)
    BigDecimal getSumByDateAndExpenseCategory(int accountFrom, OffsetDateTime datetime, ExpenseCategory expenseCategory);

    @Query("""
            select t from Transaction t
            where t.accountFrom = ?1 and t.datetime >= ?2 and t.expenseCategory = ?3
            order by t.datetime""")
    List<Transaction> findAllByDatetimeAndCategory(int accountFrom,
                                                   OffsetDateTime datetime,
                                                   ExpenseCategory expenseCategory);
}
