package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.CreateInvoiceResource;

public class CreateInvoiceCommandFromResourceAssembler {
    public static CreateInvoiceCommand toCommandFromResource(String dni, CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(
                dni,
                resource.amount(),
                resource.currency(),
                resource.dueDate()
        );
    }
}
