package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Currency;

public record UpdateEnrollmentResource(BigDecimal amount, Currency currency, String enrollmentStatus, String paymentStatus) {
}
