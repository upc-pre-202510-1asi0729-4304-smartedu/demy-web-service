package com.smartedu.demy.platform.billing.application.internal.commandservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.commands.AssignPaymentToInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.PaymentMethod;
import com.smartedu.demy.platform.billing.domain.services.InvoiceCommandService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * inherit javadoc
     */
    @Override
    public Long handle(CreateInvoiceCommand command) {
        var invoice = new Invoice(command);
        try {
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving invoice", e);
        }
        return invoice.getId();
    }

    @Override
    public Payment handle(AssignPaymentToInvoiceCommand command) {
        var invoice = invoiceRepository.findById(command.invoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        var money = new Money(command.amount(), Currency.getInstance(command.currency()));
        var method = PaymentMethod.valueOf(command.method());
        var payment = new Payment(money, method, command.paidAt());

        invoice.addPayment(payment);
        invoiceRepository.save(invoice);

        return invoice.getPayments().getLast();
    }
}
