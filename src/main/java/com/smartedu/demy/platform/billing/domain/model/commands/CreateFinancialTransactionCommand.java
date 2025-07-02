package com.smartedu.demy.platform.billing.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateFinancialTransactionCommand(
        String type,
        String category,
        String concept,
        LocalDateTime date,
        String method,
        String currency,
        BigDecimal amount,
        LocalDateTime paidAt,
        Long invoiceId
) {
}
