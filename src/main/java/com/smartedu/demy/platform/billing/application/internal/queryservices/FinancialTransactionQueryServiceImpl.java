package com.smartedu.demy.platform.billing.application.internal.queryservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import com.smartedu.demy.platform.billing.domain.model.queries.GetAllFinancialTransactionsQuery;
import com.smartedu.demy.platform.billing.domain.services.FinancialTransactionQueryService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialTransactionQueryServiceImpl implements FinancialTransactionQueryService {
    private final FinancialTransactionRepository  financialTransactionRepository;

    public FinancialTransactionQueryServiceImpl(FinancialTransactionRepository financialTransactionRepository) {
        this.financialTransactionRepository = financialTransactionRepository;
    }

    @Override
    public List<FinancialTransaction> handle(GetAllFinancialTransactionsQuery query) {
        return financialTransactionRepository.findAll();
    }
}
