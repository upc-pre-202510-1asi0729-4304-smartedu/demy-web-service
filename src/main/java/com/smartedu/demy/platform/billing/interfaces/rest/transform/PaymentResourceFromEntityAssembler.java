package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getAmount().amount(),
                entity.getAmount().currency().getCurrencyCode(),
                entity.getMethod().name(),
                entity.getPaidAt(),
                entity.getInvoiceId()
        );
    }
}
