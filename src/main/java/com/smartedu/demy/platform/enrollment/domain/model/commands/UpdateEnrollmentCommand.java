package com.smartedu.demy.platform.enrollment.domain.model.commands;

import java.math.BigDecimal;
import java.util.Currency;

public record UpdateEnrollmentCommand(Long enrollmentId, BigDecimal amount, Currency currency, String enrollmentStatus, String paymentStatus) {
    public UpdateEnrollmentCommand {
        if (enrollmentId == null || enrollmentId < 1) {
            throw new IllegalArgumentException("enrollmentId cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
        if (currency == null || (currency != Currency.getInstance("PEN") && currency != Currency.getInstance("USD"))) {
            throw new IllegalArgumentException("currency must be PEN or USD");
        }
    }
}
