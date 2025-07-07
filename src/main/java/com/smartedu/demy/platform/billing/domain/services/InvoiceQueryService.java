package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.queries.GetAllInvoicesByDniQuery;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryService {

    Optional<Invoice> handle(GetInvoiceByIdQuery query);

    List<Invoice> handle(GetAllInvoicesByDniQuery query);
}
