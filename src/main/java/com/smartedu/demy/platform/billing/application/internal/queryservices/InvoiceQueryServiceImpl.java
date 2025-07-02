package com.smartedu.demy.platform.billing.application.internal.queryservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.queries.GetAllInvoicesByDniQuery;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByIdQuery;
import com.smartedu.demy.platform.billing.domain.services.InvoiceQueryService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByIdQuery query) {
        return invoiceRepository.findById(query.invoiceId());
    }

    @Override
    public List<Invoice> handle(GetAllInvoicesByDniQuery query) {
        return invoiceRepository.findByDni(query.dni());
    }
}
