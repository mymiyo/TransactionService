package ru.benyfox.TransactionsRestApi.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.benyfox.TransactionsRestApi.dto.Transaction.TransactionDTO;

@Component
@AllArgsConstructor
public class TransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TransactionDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
