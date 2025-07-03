package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateInvoiceResource(
        String dni,
        BigDecimal amount,
        String currency,
        LocalDate dueDate) {
}
