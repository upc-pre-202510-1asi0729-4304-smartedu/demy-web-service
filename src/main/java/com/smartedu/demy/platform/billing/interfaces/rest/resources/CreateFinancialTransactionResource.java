package com.smartedu.demy.platform.billing.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateFinancialTransactionResource(
        String type,
        String category,
        String concept,
        LocalDateTime date,
        CreatePaymentResource payment
) {
}
