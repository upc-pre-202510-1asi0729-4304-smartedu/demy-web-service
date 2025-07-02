package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatePaymentResource(
        String method,
        String currency,
        BigDecimal amount,
        LocalDateTime paidAt,
        Long invoiceId
) {
}
