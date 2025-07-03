package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResource(
        Long id,
        BigDecimal amount,
        String currency,
        String method,
        LocalDateTime paidAt,
        Long invoiceId
) {
}
