package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountQueryServiceImpl implements UserAccountQueryService {
    private final UserAccountRepository repo;

    public UserAccountQueryServiceImpl(UserAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<UserAccount> findAdmins() {
        return repo.findByRole(Roles.ADMIN);
    }

    @Override
    public List<UserAccount> findTeachers() {
        return repo.findByRole(Roles.TEACHER);
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return repo.findById(id);
    }
}