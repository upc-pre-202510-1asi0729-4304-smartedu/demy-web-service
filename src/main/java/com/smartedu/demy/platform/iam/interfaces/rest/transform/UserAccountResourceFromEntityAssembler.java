package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UserAccountResource;

public class UserAccountResourceFromEntityAssembler {
    public static UserAccountResource toResource(UserAccount user) {
        return new UserAccountResource(
                user.getId(),
                user.getFullName().getFullName() ,
                user.getEmail().value(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }
}