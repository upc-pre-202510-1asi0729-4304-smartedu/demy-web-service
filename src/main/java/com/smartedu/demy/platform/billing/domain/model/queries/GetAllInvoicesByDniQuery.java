package com.smartedu.demy.platform.billing.domain.model.queries;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;

public record GetAllInvoicesByDniQuery(Dni dni) {
    public GetAllInvoicesByDniQuery {
        if (dni == null || dni.dni() == null || dni.dni().isBlank()) {
            throw new IllegalArgumentException("Student id cannot be null or empty.");
        }
    }
}
