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
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotCreatedException;
import ru.benyfox.TransactionsRestApi.exceptions.Transaction.TransactionNotFoundException;
import ru.benyfox.TransactionsRestApi.models.Limits.Limit;
import ru.benyfox.TransactionsRestApi.models.Limits.ProductsLimits;
import ru.benyfox.TransactionsRestApi.models.Limits.ServicesLimits;
import ru.benyfox.TransactionsRestApi.repositories.jpa.ProductsLimitsRepository;
import ru.benyfox.TransactionsRestApi.repositories.jpa.ServicesLimitsRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LimitsService {
    private final ProductsLimitsRepository productsLimitsRepository;
    private final ServicesLimitsRepository servicesLimitsRepository;
    private final ModelMapper modelMapper;

    public List<LimitResponseDTO> getLimitsList(ExpenseCategory category) {
        if (category.equals(ExpenseCategory.product))
            return productsLimitsRepository.findAll().stream().map(this::convertToLimitResponseDTO)
                    .collect(Collectors.toList());

        if (category.equals(ExpenseCategory.service))
            return servicesLimitsRepository.findAll().stream().map(this::convertToLimitResponseDTO)
                    .collect(Collectors.toList());


        return null;
    }

    public LimitResponseDTO findOne(ExpenseCategory category, int id) {
        if (category.equals(ExpenseCategory.product))
            return convertToLimitResponseDTO(productsLimitsRepository.findById(id)
                    .orElseThrow(TransactionNotFoundException::new));

        if (category.equals(ExpenseCategory.service))
            return convertToLimitResponseDTO(servicesLimitsRepository.findById(id)
                    .orElseThrow(TransactionNotFoundException::new));

        return null;
    }

    public Limit findOneByAccountName(ExpenseCategory category, String accountFrom) {
        if (category.equals(ExpenseCategory.product))
            return productsLimitsRepository.findTopByAccountNumberOrderByLimitDatetimeDesc(accountFrom)
                    .orElseThrow(TransactionNotFoundException::new);

        if (category.equals(ExpenseCategory.service))
            return servicesLimitsRepository.findTopByAccountNumberOrderByLimitDatetimeDesc(accountFrom)
                    .orElseThrow(TransactionNotFoundException::new);

        return null;
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

        if(limitCreateDTO.getCategory().equals(ExpenseCategory.product))
            productsLimitsRepository.save(modelMapper.map(limit, ProductsLimits.class));

        if(limitCreateDTO.getCategory().equals(ExpenseCategory.service))
            servicesLimitsRepository.save(modelMapper.map(limit, ServicesLimits.class));
    }

    private Limit convertToLimit(LimitCreateDTO limitCreateDTO) {
        return modelMapper.map(limitCreateDTO, Limit.class);
    }

    private LimitResponseDTO convertToLimitResponseDTO(Limit limit) {
        return modelMapper.map(limit, LimitResponseDTO.class);
    }
}
