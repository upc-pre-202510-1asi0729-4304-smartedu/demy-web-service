package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateFinancialTransactionCommand;
import com.smartedu.demy.platform.billing.domain.model.commands.RegisterPaymentCommand;

import java.util.Optional;

public interface FinancialTransactionCommandService {

    Optional<FinancialTransaction> handle(CreateFinancialTransactionCommand command);

    Optional<FinancialTransaction> handle(RegisterPaymentCommand command);
}
