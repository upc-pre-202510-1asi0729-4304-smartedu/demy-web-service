package com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.resources.Email;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    List<UserAccount> findByUserId(UserId userId);
    List<UserAccount> findByRole(Roles roles);
    Optional<UserAccount> findByEmail_Value(String value);
}