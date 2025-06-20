package com.smartedu.demy.platform.billing.domain.services;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;

public interface InvoiceCommandService {
    Long handle (CreateInvoiceCommand command);
}
