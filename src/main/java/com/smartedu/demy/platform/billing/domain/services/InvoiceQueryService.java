package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByIdQuery;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByStudentIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryService {
    Optional<Invoice> handle(GetInvoiceByIdQuery query);

    List<Invoice> handle(GetInvoiceByStudentIdQuery query);
}
