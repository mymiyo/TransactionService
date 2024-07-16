package ru.benyfox.TransactionsRestApi.repositories.cassandra;

import org.springframework.stereotype.Repository;
import ru.benyfox.TransactionsRestApi.models.ExchangeRate;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends CassandraRepository<ExchangeRate, UUID> {
    Optional<ExchangeRate> findTopByPair(String pair);
}
