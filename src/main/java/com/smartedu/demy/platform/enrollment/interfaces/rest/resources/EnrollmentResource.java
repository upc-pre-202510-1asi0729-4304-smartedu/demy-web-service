package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public record EnrollmentResource(Long id, Long studentId, Long academicPeriodId, Long weeklyScheduleId, BigDecimal amount, Currency currency, Date createdAt, String enrollmentStatus, String paymentStatus) {
    public EnrollmentResource {
       if (id == null || amount == null || currency == null) {
           throw new IllegalArgumentException("Both enrollmentId and amount and currency are null");
       }
    }
}
