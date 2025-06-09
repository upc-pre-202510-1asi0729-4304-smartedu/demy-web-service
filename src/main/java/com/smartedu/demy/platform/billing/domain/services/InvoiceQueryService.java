package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByStudentIdQuery;

import java.util.List;

public interface InvoiceQueryService {
    List<Invoice> handle(GetInvoiceByStudentIdQuery query);
}
