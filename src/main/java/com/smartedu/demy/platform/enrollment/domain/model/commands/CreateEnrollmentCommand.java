package com.smartedu.demy.platform.enrollment.domain.model.commands;

import java.math.BigDecimal;
import java.util.Currency;

public record CreateEnrollmentCommand(Long studentId, Long periodId, String weeklyScheduleName, BigDecimal amount, Currency currency, String enrollmentStatus, String paymentStatus ) {
    public CreateEnrollmentCommand {
        if (studentId == null || studentId < 1) {
            throw new IllegalArgumentException("studentId cannot be null or blank");
        }
        if (periodId == null || periodId < 1) {
            throw new IllegalArgumentException("periodId cannot be null or blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount cannot be null or negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency cannot be null");
        }
    }
}
