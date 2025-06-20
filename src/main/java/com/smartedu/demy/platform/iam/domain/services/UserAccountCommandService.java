package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.*;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UpdateAdminResource;

public interface UserAccountCommandService {
    UserAccount createTeacher(CreateTeacherResource resource);

    UserAccount updateTeacher(Long id, UpdateTeacherResource resource);
    UserAccount updateAdmin(Long id,  UpdateAdminResource  resource);

    UserAccount signInAdmin(SignInAdminResource resource);
    UserAccount signInTeacher(SignInTeacherResource resource);
    UserAccount signUpAdmin(SignUpAdminResource resource);

    void deleteTeacher(Long id);

    void resetPassword(ResetPasswordResource resource);
}