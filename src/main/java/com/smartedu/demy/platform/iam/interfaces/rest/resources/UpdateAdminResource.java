package com.smartedu.demy.platform.iam.interfaces.rest.resources;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.resources.Email;
import com.smartedu.demy.platform.iam.domain.model.resources.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import lombok.Data;

@Data
public class UpdateAdminResource {
    private String firstName;
    private String lastName;
    private String email;

    public void applyTo(UserAccount userAccount) {
        FullName updatedName = new FullName(firstName, lastName);
        Email updatedEmail = new Email(email);

        userAccount.updateProfile(updatedName, updatedEmail);
        userAccount.updateStatus(AccountStatus.ACTIVE);

    }
}
