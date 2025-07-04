package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import com.smartedu.demy.platform.billing.domain.model.queries.GetAllFinancialTransactionsQuery;

import java.util.List;

public interface FinancialTransactionQueryService {

    List<FinancialTransaction> handle(GetAllFinancialTransactionsQuery query);
}
