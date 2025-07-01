package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.time.LocalDate;

public record InvoiceResource(
        Long id,
        String dni,
        String amount,
        String currency,
        LocalDate dueDate,
        String status
) {
}
