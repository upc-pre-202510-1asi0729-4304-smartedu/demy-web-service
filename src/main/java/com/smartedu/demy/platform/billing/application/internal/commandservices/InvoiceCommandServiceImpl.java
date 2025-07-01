package com.smartedu.demy.platform.billing.application.internal.commandservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.services.InvoiceCommandService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

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
}
