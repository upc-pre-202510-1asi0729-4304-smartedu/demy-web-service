package com.smartedu.demy.platform.billing.domain.model.commands;

public record RegisterPaymentCommand(
        Long invoiceId,
        String method
) {
}
