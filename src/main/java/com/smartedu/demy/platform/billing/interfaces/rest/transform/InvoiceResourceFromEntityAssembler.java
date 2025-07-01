package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.InvoiceResource;

public class InvoiceResourceFromEntityAssembler {
    public static InvoiceResource toResourceFromEntity(Invoice entity) {
        return new InvoiceResource(
                entity.getId(),
                entity.getDni().dni(),
                entity.getAmount().amount().toString(),
                entity.getAmount().currency().toString(),
                entity.getDueDate(),
                entity.getStatus().name()
                );
    }
}
