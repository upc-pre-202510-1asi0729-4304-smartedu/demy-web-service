package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.time.LocalDateTime;

public record FinancialTransactionResource(
        Long id,
        String type,
        String category,
        String concept,
        LocalDateTime date,
        PaymentResource payment
) {
}
