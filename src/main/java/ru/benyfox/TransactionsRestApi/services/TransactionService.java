package ru.benyfox.TransactionsRestApi.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.benyfox.TransactionsRestApi.dto.Transaction.TransactionDTO;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotCreatedException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotFoundException;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;
import ru.benyfox.TransactionsRestApi.models.Transaction;
import ru.benyfox.TransactionsRestApi.repositories.TransactionRepository;
import ru.benyfox.TransactionsRestApi.util.TransactionValidator;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final LimitsService limitsService;
    private final ModelMapper modelMapper;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public TransactionDTO findOne(int id) {
        return convertToTransactionDTO(transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new));
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

        transactionRepository.save(transaction);

        Limit currentLimit = limitsService
                .findOneByAccountName(transaction.getExpenseCategory(), transaction.getAccountFrom());


        // TODO: сделать конвертацию валют транзакций в доллары (валюта лимита)
        long total = transactionRepository
                .findTransactionsSumByDate(transaction.getAccountFrom(), currentLimit.getLimitDatetime());

        if (total >= currentLimit.getLimitSum()) currentLimit.setLimitExceeded(true);
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
}
