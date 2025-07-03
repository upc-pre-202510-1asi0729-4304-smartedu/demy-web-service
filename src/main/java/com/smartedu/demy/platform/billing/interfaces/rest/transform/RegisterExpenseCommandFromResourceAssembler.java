package com.smartedu.demy.platform.billing.interfaces.rest.transform;

import com.smartedu.demy.platform.billing.domain.model.commands.RegisterExpenseCommand;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.RegisterExpenseResource;

public class RegisterExpenseCommandFromResourceAssembler {
    public static RegisterExpenseCommand toCommandFromResource(RegisterExpenseResource resource) {
        return new RegisterExpenseCommand(
                resource.category(),
                resource.concept(),
                resource.method(),
                resource.currency(),
                resource.amount(),
                resource.paidAt()
        );
    }
}
