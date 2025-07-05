package com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction,Long> {
}
