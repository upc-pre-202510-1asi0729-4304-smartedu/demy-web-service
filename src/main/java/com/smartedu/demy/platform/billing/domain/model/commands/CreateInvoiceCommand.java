package com.smartedu.demy.platform.billing.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateInvoiceCommand(Long studentId, BigDecimal amount, String currency, LocalDate dueDate) {
}
