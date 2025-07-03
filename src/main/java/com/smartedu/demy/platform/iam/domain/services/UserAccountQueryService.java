package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllAdminsQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllTeachersQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetTeacherByFullNameQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetUserAccountByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserAccountQueryService {

    List<UserAccount> handle(GetAllAdminsQuery query);

    List<UserAccount> handle(GetAllTeachersQuery query);

    Optional<UserAccount> handle(GetUserAccountByIdQuery query);

    Optional<UserAccount> handle(GetTeacherByFullNameQuery query);
}