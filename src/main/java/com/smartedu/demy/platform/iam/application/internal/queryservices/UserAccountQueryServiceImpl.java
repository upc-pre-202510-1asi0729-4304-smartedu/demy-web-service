package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountQueryServiceImpl {
    private final UserAccountRepository userAccountRepository;

    public UserAccountQueryServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public Optional<UserAccount> findById(Long userId) {
        return userAccountRepository.findById(userId);
    }
}