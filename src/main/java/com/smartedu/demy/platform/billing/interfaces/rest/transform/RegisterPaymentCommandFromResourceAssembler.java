package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.commands.RegisterPaymentCommand;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.RegisterPaymentResource;

public class RegisterPaymentCommandFromResourceAssembler {
    public static RegisterPaymentCommand toCommandFromResource(Long invoiceId, RegisterPaymentResource resource) {
        return new RegisterPaymentCommand(
                invoiceId,
                resource.method()
        );
    }
}
