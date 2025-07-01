package com.smartedu.demy.platform.billing.application.internal.commandservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateFinancialTransactionCommand;
import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.PaymentMethod;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.TransactionCategory;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.TransactionType;
import com.smartedu.demy.platform.billing.domain.services.FinancialTransactionCommandService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.FinancialTransactionRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Optional;

@Service
public class FinancialTransactionCommandServiceImpl implements FinancialTransactionCommandService {

    private final FinancialTransactionRepository financialTransactionRepository;

    public FinancialTransactionCommandServiceImpl(FinancialTransactionRepository financialTransactionRepository) {
        this.financialTransactionRepository = financialTransactionRepository;
    }

    @Override
    public Optional<FinancialTransaction> handle(CreateFinancialTransactionCommand command) {
        var money = new Money(command.amount(), Currency.getInstance(command.currency()));
        var method = PaymentMethod.valueOf(command.method());

        var payment = new Payment(
                command.invoiceId(),
                money,
                method,
                command.paidAt()
        );

        var financialTransaction = new FinancialTransaction(
                TransactionType.valueOf(command.type()),
                TransactionCategory.valueOf(command.category()),
                command.concept(),
                command.date(),
                payment
        );

        try {
            financialTransactionRepository.save(financialTransaction);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving financial transaction", e);
        }
        return Optional.of(financialTransaction);
    }
}
