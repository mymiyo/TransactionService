package ru.benyfox.TransactionsRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;
import ru.benyfox.TransactionsRestApi.models.Limits.ProductsLimits;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsLimitsRepository extends JpaRepository<ProductsLimits, Integer> {
    Optional<ProductsLimits> findTopByAccountNumberOrderByLimitDatetimeDesc(String accountNumber);

}
