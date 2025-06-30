package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssignPaymentToInvoiceResource(
        BigDecimal amount,
        String currency,
        String method,
        LocalDateTime paidAt
) {}
