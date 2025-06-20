package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.commands.AssignPaymentToInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.model.entities.Payment;

public interface InvoiceCommandService {
    Long handle(CreateInvoiceCommand command);

    Payment handle(AssignPaymentToInvoiceCommand command);
}
