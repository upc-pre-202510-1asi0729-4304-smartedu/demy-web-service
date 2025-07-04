package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UserAccountResource;

/**
 * Assembler class to convert {@link UserAccount} entities
 * into {@link UserAccountResource} DTOs for REST responses.
 */
public class UserAccountResourceFromEntityAssembler {
    /**
     * Converts a {@link UserAccount} entity into a {@link UserAccountResource}.
     *
     * @param user the UserAccount entity to convert
     * @return the corresponding UserAccountResource with user details
     */
    public static UserAccountResource toResourceFromEntity(UserAccount user) {
        return new UserAccountResource(
                user.getId(),
                user.getFullName().getFullName(),
                user.getEmail().value(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }
}