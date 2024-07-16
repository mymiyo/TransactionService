package ru.benyfox.TransactionsRestApi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.benyfox.TransactionsRestApi.dto.Limit.ExceededLimitDto;
import ru.benyfox.TransactionsRestApi.dto.Transaction.TransactionDTO;
import ru.benyfox.TransactionsRestApi.dto.Transaction.TransactionOverdraftDTO;
import ru.benyfox.TransactionsRestApi.exceptions.ExchangeRate.ExchangeRateNotFoundException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotCreatedException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotFoundException;
import ru.benyfox.TransactionsRestApi.models.ExchangeRate;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;
import ru.benyfox.TransactionsRestApi.models.Transaction;
import ru.benyfox.TransactionsRestApi.repositories.jpa.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final ExchangePairsService exchangePairsService;
    private final TransactionRepository transactionRepository;
    private final LimitService limitService;
    private final ModelMapper modelMapper;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public TransactionDTO findOne(int id) {
        return convertToTransactionDTO(transactionRepository.findById(id)
                .orElseThrow(TransactionNotFoundException::new));
    }


    public Set<TransactionOverdraftDTO> findExceeded(int accountNumber) {
        List<Limit> exceededLimits = limitService.findAllExceededLimitsByAccountNumber(accountNumber);

        return exceededLimits.stream()
                .flatMap(limit -> limit.getExceededTransactions().stream()
                        .map(transaction -> convertToTransactionOverdraftDTO(transaction, limit))) // Преобразуем Transaction в DTO, передавая и limit
                        .collect(Collectors.toSet());
    }

    @Transactional
    public void save(TransactionDTO transactionDTO, BindingResult bindingResult) {
        Transaction transaction = convertToTransaction(transactionDTO);
        transaction.setDatetime(OffsetDateTime.now(ZoneOffset.UTC));

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new TransactionNotCreatedException(errorMessage.toString());
        }

        Limit limit = limitService.findOneByAccountNumber(transaction.getAccountFrom(),
                transaction.getExpenseCategory());
        transaction.setLimit(limit);

        transactionRepository.save(transaction);
        checkTransactionForLimit(transaction);
    }

    private void checkTransactionForLimit(Transaction transaction) {
        Limit limit = transaction.getLimit();

        BigDecimal total = transactionRepository.getSumByDateAndExpenseCategory(transaction.getAccountFrom(),
                limit.getLimitDatetime(),
                transaction.getExpenseCategory());

        BigDecimal convertedTotal;
        try {
            convertedTotal = convertToLimitCurrencyWithCheck(total,
                    limit.getLimitCurrencyShortname(),
                    transaction.getCurrencyShortname());
        } catch (ExchangeRateNotFoundException e) {
            throw new TransactionNotCreatedException("The corresponding limit exchange rate was not found" +
                    " for the transferred transaction");
        }

        if (convertedTotal.compareTo(limit.getLimitSum()) >= 0) {
            limit.setLimitExceeded(true);
            limit.getExceededTransactions().add(transaction);
        }

        transactionRepository.save(transaction);
    }

    BigDecimal convertToLimitCurrencyWithCheck(BigDecimal transactionSum,
                                               String limitCurrency,
                                               String transactionCurrency) {
        if (!limitCurrency.equals(transactionCurrency)) {
            ExchangeRate exchangeRate = exchangePairsService.getLastExchangeRate(
                    limitCurrency + "/" + transactionCurrency);

            return transactionSum.divide(exchangeRate.getValue(), 3, RoundingMode.HALF_UP);
        } else {
            return transactionSum;
        }
    }

    public List<TransactionDTO> getTransactionsList() {
        return this.findAll().stream().map(this::convertToTransactionDTO)
                .collect(Collectors.toList());
    }

    private Transaction convertToTransaction(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);
    }

    private TransactionDTO convertToTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    private TransactionOverdraftDTO convertToTransactionOverdraftDTO(Transaction transaction, Limit limit) {
        TransactionOverdraftDTO dto = modelMapper.map(transaction, TransactionOverdraftDTO.class);
        dto.setLimit(convertToExceededLimitDTO(limit));
        return dto;
    }

    private ExceededLimitDto convertToExceededLimitDTO(Limit limit) {
        return modelMapper.map(limit, ExceededLimitDto.class);
    }
}
