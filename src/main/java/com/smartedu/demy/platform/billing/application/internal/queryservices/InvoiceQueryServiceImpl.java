package com.smartedu.demy.platform.billing.application.internal.queryservices;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByStudentIdQuery;
import com.smartedu.demy.platform.billing.domain.services.InvoiceQueryService;
import com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> handle(GetInvoiceByStudentIdQuery query) {
        return invoiceRepository.findByStudentId(query.studentId());
    }
}
