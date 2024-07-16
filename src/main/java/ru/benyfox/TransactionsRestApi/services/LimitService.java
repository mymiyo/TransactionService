package ru.benyfox.TransactionsRestApi.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.benyfox.TransactionsRestApi.dto.Limit.LimitCreateDTO;
import ru.benyfox.TransactionsRestApi.dto.Limit.LimitResponseDTO;
import ru.benyfox.TransactionsRestApi.enums.ExpenseCategory;
import ru.benyfox.TransactionsRestApi.exceptions.Limit.LimitNotFoundException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotCreatedException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotFoundException;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;
import ru.benyfox.TransactionsRestApi.repositories.jpa.LimitRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LimitService {
    private final LimitRepository limitsRepository;
    private final ModelMapper modelMapper;

    public List<LimitResponseDTO> getLimitsList(ExpenseCategory category) {
        return limitsRepository.findAllByExpenseCategory(category).stream()
                .map(this::convertToLimitResponseDTO)
                .collect(Collectors.toList());
    }

    public LimitResponseDTO findOneById(int id) {
        return convertToLimitResponseDTO(limitsRepository.findById(id).orElseThrow(LimitNotFoundException::new));
    }

    public Limit findOneByAccountNumber(int accountFrom, ExpenseCategory category) {
        return limitsRepository.findTopByAccountNumberAndExpenseCategoryOrderByLimitDatetimeDesc(accountFrom, category)
                .orElseThrow(TransactionNotFoundException::new);
    }

    public List<Limit> findAllExceededLimitsByAccountNumber(int id) {
        return limitsRepository.findAllExceededByAccountNumber(id);
    }

    @Transactional
    public void save(LimitCreateDTO limitCreateDTO, BindingResult bindingResult) {

        Limit limit = convertToLimit(limitCreateDTO);
        limit.setLimitDatetime(OffsetDateTime.now(ZoneOffset.UTC));

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

        limitsRepository.save(limit);
    }

    private Limit convertToLimit(LimitCreateDTO limitCreateDTO) {
        return modelMapper.map(limitCreateDTO, Limit.class);
    }

    private LimitResponseDTO convertToLimitResponseDTO(Limit limit) {
        return modelMapper.map(limit, LimitResponseDTO.class);
    }
}
