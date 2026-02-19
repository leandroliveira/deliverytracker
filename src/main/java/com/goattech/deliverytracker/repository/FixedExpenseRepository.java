package com.goattech.deliverytracker.repository;

import com.goattech.deliverytracker.model.FixedExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FixedExpenseRepository extends JpaRepository<FixedExpense, UUID> {
    List<FixedExpense> findByUserId(UUID userId);
}
