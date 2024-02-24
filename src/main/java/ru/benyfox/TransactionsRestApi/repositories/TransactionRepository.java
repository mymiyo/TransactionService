package ru.benyfox.TransactionsRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.benyfox.TransactionsRestApi.models.Transaction;

import java.time.OffsetDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT SUM(t.sum) FROM Transaction t WHERE t.accountFrom = ?1 AND t.datetime > ?2")
    public Long findTransactionsSumByDate(String accountFrom, OffsetDateTime datetime);

}
