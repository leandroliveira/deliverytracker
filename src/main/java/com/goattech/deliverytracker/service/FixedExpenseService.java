package com.goattech.deliverytracker.service;

import com.goattech.deliverytracker.exception.BusinessException;
import com.goattech.deliverytracker.model.FixedExpense;
import com.goattech.deliverytracker.model.dto.FixedExpenseDto;
import com.goattech.deliverytracker.repository.FixedExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FixedExpenseService {

    private final FixedExpenseRepository fixedExpenseRepository;

    public FixedExpenseService(FixedExpenseRepository fixedExpenseRepository) {
        this.fixedExpenseRepository = fixedExpenseRepository;
    }

    public List<FixedExpenseDto> getFixedExpensesForUser(UUID userId) {
        return fixedExpenseRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public FixedExpenseDto createFixedExpense(FixedExpenseDto dto, UUID userId) {
        FixedExpense expense = new FixedExpense();
        updateEntityFromDto(expense, dto);
        expense.setUserId(userId);
        return toDto(fixedExpenseRepository.save(expense));
    }

    @Transactional
    public FixedExpenseDto updateFixedExpense(UUID id, FixedExpenseDto dto, UUID userId) {
        FixedExpense expense = fixedExpenseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Fixed expense not found"));

        if (!expense.getUserId().equals(userId)) {
            throw new BusinessException("Access denied");
        }

        updateEntityFromDto(expense, dto);
        return toDto(fixedExpenseRepository.save(expense));
    }

    @Transactional
    public void deleteFixedExpense(UUID id, UUID userId) {
        FixedExpense expense = fixedExpenseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Fixed expense not found"));

        if (!expense.getUserId().equals(userId)) {
            throw new BusinessException("Access denied");
        }

        fixedExpenseRepository.delete(expense);
    }

    private void updateEntityFromDto(FixedExpense entity, FixedExpenseDto dto) {
        if (dto.categoryId() != null) {
            entity.setCategoryId(dto.categoryId());
        }
        if (dto.vehicleId() != null) {
            entity.setVehicleId(dto.vehicleId());
        }
        if (dto.amount() != null) {
            entity.setAmount(dto.amount());
        }
        if (dto.startDate() != null) {
            entity.setStartDate(dto.startDate());
        }
        if (dto.endDate() != null) {
            entity.setEndDate(dto.endDate());
        }
        if (dto.isActive() != null) {
            entity.setActive(dto.isActive());
        }
    }

    private FixedExpenseDto toDto(FixedExpense entity) {
        return new FixedExpenseDto(
                entity.getId(),
                entity.getCategoryId(),
                entity.getVehicleId(),
                entity.getAmount(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getActive(),
                entity.getCreatedAt());
    }
}
