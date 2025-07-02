package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.CreateInvoiceResource;

public class CreateInvoiceCommandFromResourceAssembler {
    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(
                resource.dni(),
                resource.amount(),
                resource.currency(),
                resource.dueDate()
        );
    }
}
