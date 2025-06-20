package com.smartedu.demy.platform.billing.domain.model.queries;

public record GetInvoiceByIdQuery(Long invoiceId) {
    public GetInvoiceByIdQuery {
        if (invoiceId == null || invoiceId <= 0) {
            throw new IllegalArgumentException("Invoice id is required");
        }
    }
}
