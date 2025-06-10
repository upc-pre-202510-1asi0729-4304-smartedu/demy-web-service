package com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}