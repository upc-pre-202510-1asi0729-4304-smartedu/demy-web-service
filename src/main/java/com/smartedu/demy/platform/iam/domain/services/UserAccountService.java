package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;

public interface UserAccountService {
    UserAccount register(UserAccount userAccount);
}