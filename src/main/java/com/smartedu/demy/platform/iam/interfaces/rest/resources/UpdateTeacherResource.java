package com.smartedu.demy.platform.iam.interfaces.rest.resources;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.resources.Email;
import com.smartedu.demy.platform.iam.domain.model.resources.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import com.smartedu.demy.platform.shared.domain.model.services.PasswordHasher;
import lombok.Data;

@Data
public class UpdateTeacherResource {
    private String firstName;
    private String lastName;
    private String email;
    private String password; // opcional

    public void applyTo(UserAccount userAccount) {
        userAccount.updateProfile(
                new FullName(firstName, lastName),
                new Email(email)
        );
        userAccount.updateStatus(AccountStatus.ACTIVE);

        if (password != null && !password.isBlank()) {
            userAccount.updatePassword(PasswordHasher.hash(password));
        }
    }
}
