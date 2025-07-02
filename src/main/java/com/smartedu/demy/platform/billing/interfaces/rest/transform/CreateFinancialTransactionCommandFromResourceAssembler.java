package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateFinancialTransactionCommand;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.CreateFinancialTransactionResource;

public class CreateFinancialTransactionCommandFromResourceAssembler {
    public static CreateFinancialTransactionCommand toCommandFromResource(CreateFinancialTransactionResource resource) {
        return new CreateFinancialTransactionCommand(
                resource.type(),
                resource.category(),
                resource.concept(),
                resource.date(),
                resource.payment().method(),
                resource.payment().currency(),
                resource.payment().amount(),
                resource.payment().paidAt(),
                resource.payment().invoiceId()
        );
    }
}
