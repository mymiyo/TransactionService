package ru.benyfox.TransactionsRestApi.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.benyfox.TransactionsRestApi.models.Transaction;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT SUM(t.sum) FROM Transaction t WHERE t.accountFrom = ?1 AND t.datetime > ?2")
    public Long findSumByDate(String accountFrom, OffsetDateTime datetime);

    @Query(value = "SELECT t FROM Transaction t WHERE t.accountFrom = ?1 AND t.datetime > ?2 ORDER BY t.datetime ASC")
    public List<Transaction> findExceededByDate(String accountFrom, OffsetDateTime datetime);
}
