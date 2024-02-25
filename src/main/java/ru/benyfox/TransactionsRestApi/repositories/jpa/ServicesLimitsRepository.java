package ru.benyfox.TransactionsRestApi.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.benyfox.TransactionsRestApi.models.Limits.ServicesLimits;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesLimitsRepository extends JpaRepository<ServicesLimits, Integer> {
    Optional<ServicesLimits> findTopByAccountNumberOrderByLimitDatetimeDesc(String accountNumber);
}
