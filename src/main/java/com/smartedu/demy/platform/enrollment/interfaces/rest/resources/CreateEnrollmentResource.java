package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Currency;

public record CreateEnrollmentResource(Long studentId, Long academicPeriodId, String weeklyScheduleName, BigDecimal amount, Currency currency, String enrollmentStatus, String paymentStatus) {
    public CreateEnrollmentResource {
        if (studentId == null || studentId < 1) {
            throw new IllegalArgumentException("studentId cannot be null");
        }
        if (academicPeriodId == null || academicPeriodId < 1) {
            throw new IllegalArgumentException("periodId cannot be null");
        }
        if ( amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount cannot be null");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency cannot be null");
        }

    }
}
