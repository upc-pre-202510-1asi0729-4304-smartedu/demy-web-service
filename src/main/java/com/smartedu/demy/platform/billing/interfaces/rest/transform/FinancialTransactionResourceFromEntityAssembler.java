package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.aggregates.FinancialTransaction;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.FinancialTransactionResource;

public class FinancialTransactionResourceFromEntityAssembler {
    public static FinancialTransactionResource toResourceFromEntity(FinancialTransaction entity) {
        return new FinancialTransactionResource(
                entity.getId(),
                entity.getType().name(),
                entity.getCategory().name(),
                entity.getConcept(),
                entity.getDate(),
                PaymentResourceFromEntityAssembler.toResourceFromEntity(entity.getPayment())
        );
    }
}
