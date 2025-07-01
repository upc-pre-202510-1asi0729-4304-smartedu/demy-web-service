package com.smartedu.demy.platform.billing.application.internal.commandservices;

import com.smartedu.demy.platform.billing.application.internal.outboundservices.acl.ExternalEnrollmentService;
import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.services.InvoiceCommandService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final InvoiceRepository invoiceRepository;
    private final ExternalEnrollmentService externalEnrollmentService;

    public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository, ExternalEnrollmentService externalEnrollmentService) {
        this.invoiceRepository = invoiceRepository;
        this.externalEnrollmentService = externalEnrollmentService;
    }

    /**
     * inherit javadoc
     */
    @Override
    public Long handle(CreateInvoiceCommand command) {
        var name = externalEnrollmentService.fetchStudentNameByDni(command.dni());
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Student not found with dni %s".formatted(command.dni()));
        }

        var invoice = new Invoice(
                new Dni(command.dni()),
                name.get(),
                new Money(command.amount(), Currency.getInstance(command.currency())),
                command.dueDate()
        );
        try {
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving invoice", e);
        }
        return invoice.getId();
    }
}
