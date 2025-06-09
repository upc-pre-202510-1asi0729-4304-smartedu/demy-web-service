package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.time.LocalDate;

public record InvoiceResource(
        Long id,
        String studentId,
        String amount,
        String currency,
        LocalDate dueDate,
        String status
) {
}
