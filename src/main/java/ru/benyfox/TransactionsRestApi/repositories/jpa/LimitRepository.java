package ru.benyfox.TransactionsRestApi.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;

import java.util.List;
import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Integer> {
    @Query("select l from Limit l where l.expenseCategory = ?1")
    List<Limit> findAllByExpenseCategory(ExpenseCategory expenseCategory);

    @Query("select l from Limit l where l.expenseCategory = ?1")
    Optional<Limit> findOneByExpenseCategory(ExpenseCategory expenseCategory);

    Optional<Limit> findTopByAccountNumberAndExpenseCategoryOrderByLimitDatetimeDesc(int accountNumber,
                                                                                     ExpenseCategory expenseCategory);

    @Query("select l from Limit l where l.accountNumber = ?1 and l.limitExceeded = true")
    List<Limit> findAllExceededByAccountNumber(@NonNull Integer accountNumber);
}