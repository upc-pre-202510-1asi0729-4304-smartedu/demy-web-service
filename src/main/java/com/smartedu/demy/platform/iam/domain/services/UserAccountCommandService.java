package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.commands.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface UserAccountCommandService {

    UserAccount handle(SignUpAdminCommand command);

    ImmutablePair<UserAccount, String> handle(SignInUserAccountCommand command);

    UserAccount handle(CreateTeacherCommand command);

    UserAccount handle(UpdateUserAccountCommand command);

    void handle(DeleteUserAccountCommand command);

    void handle(ResetPasswordCommand command);
}