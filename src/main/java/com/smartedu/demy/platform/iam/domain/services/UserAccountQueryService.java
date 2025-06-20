package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountQueryService {
    List<UserAccount> findAdmins();
    List<UserAccount> findTeachers();
    Optional<UserAccount> findById(Long id);

}