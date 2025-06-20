package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;

import java.math.BigDecimal;
import java.util.Currency;

public record EnrollmentResource(Long enrollmentId, Long studentId, Long academicPeriodId, BigDecimal amount, Currency currency, String enrollmentStatus, String paymentStatus) {
    public EnrollmentResource {
       if (enrollmentId == null || amount == null || currency == null) {
           throw new IllegalArgumentException("Both enrollmentId and amount and currency are null");
       }
    }
}
