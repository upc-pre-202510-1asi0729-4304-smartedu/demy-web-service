package com.smartedu.demy.platform.billing.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssignPaymentToInvoiceCommand(
        Long invoiceId,
        BigDecimal amount,
        String currency,
        String method,
        LocalDateTime paidAt
) {}
