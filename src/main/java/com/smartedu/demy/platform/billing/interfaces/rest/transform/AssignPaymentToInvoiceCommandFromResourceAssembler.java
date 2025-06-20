package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.commands.AssignPaymentToInvoiceCommand;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.AssignPaymentToInvoiceResource;

public class AssignPaymentToInvoiceCommandFromResourceAssembler {
    public static AssignPaymentToInvoiceCommand toCommandFromResource(
            Long invoiceId,
            AssignPaymentToInvoiceResource resource
    ) {
        return new AssignPaymentToInvoiceCommand(
                invoiceId,
                resource.amount(),
                resource.currency(),
                resource.method(),
                resource.paidAt()
        );
    }
}
