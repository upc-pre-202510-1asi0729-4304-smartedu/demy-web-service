package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateInvoiceResource(
        BigDecimal amount,
        String currency,
        LocalDate dueDate) {
}
