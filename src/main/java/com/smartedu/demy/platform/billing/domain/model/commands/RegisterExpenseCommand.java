package com.smartedu.demy.platform.billing.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegisterExpenseCommand(
        String category,
        String concept,
        String method,
        String currency,
        BigDecimal amount,
        LocalDateTime paidAt
) {
}
