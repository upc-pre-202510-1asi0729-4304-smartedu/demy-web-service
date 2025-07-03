package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllAdminsQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllTeachersQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetTeacherByFullNameQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetUserAccountByIdQuery;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountQueryServiceImpl implements UserAccountQueryService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountQueryServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public List<UserAccount> handle(GetAllAdminsQuery query) {
        return userAccountRepository.findByRole(Roles.ADMIN);
    }

    @Override
    public List<UserAccount> handle(GetAllTeachersQuery query) {
        return userAccountRepository.findByRole(Roles.TEACHER);
    }

    @Override
    public Optional<UserAccount> handle(GetUserAccountByIdQuery query) {
        return userAccountRepository.findById(query.id());
    }

    @Override
    public Optional<UserAccount> handle(GetTeacherByFullNameQuery query) {
        return userAccountRepository.findByFullName(query.fullname());
    }
}