package com.smartedu.demy.platform.billing.application.internal.commandservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateFinancialTransactionCommand;
import com.smartedu.demy.platform.billing.domain.model.commands.RegisterPaymentCommand;
import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.PaymentMethod;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.TransactionCategory;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.TransactionType;
import com.smartedu.demy.platform.billing.domain.services.FinancialTransactionCommandService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.FinancialTransactionRepository;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

@Service
public class FinancialTransactionCommandServiceImpl implements FinancialTransactionCommandService {

    private final FinancialTransactionRepository financialTransactionRepository;
    private final InvoiceRepository invoiceRepository;

    public FinancialTransactionCommandServiceImpl(
            FinancialTransactionRepository financialTransactionRepository,
            InvoiceRepository invoiceRepository
    ) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.invoiceRepository = invoiceRepository;
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

    @Override
    @Transactional
    public Optional<FinancialTransaction> handle(RegisterPaymentCommand command) {
        var invoice = invoiceRepository.findById(command.invoiceId());
        if (invoice.isEmpty()) {
            throw new IllegalArgumentException("Invoice not found with invoice id %s".formatted(command.invoiceId()));
        }

        if (invoice.get().isPaid()) {
            throw new IllegalStateException("Invoice is already paid");
        }

        var amount = new Money(invoice.get().getAmount().amount(), Currency.getInstance(invoice.get().getAmount().currency().getCurrencyCode()));
        var payment = new Payment(
                command.invoiceId(),
                amount,
                PaymentMethod.valueOf(command.method()),
                LocalDateTime.now()
        );

        invoice.get().markAsPaid();

        var financialTransaction = new FinancialTransaction(
                TransactionType.INCOME,
                TransactionCategory.STUDENTS,
                "Paid student invoice",
                LocalDateTime.now(),
                payment
        );

        try {
            invoiceRepository.save(invoice.get());
            financialTransactionRepository.save(financialTransaction);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving invoice or financial transaction", e);
        }

        return Optional.of(financialTransaction);
    }
}
