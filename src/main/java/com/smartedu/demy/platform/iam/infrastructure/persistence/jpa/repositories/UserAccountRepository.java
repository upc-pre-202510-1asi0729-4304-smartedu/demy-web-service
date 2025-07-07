package com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Email;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository interface for managing {@link UserAccount} entities.
 * <p>
 * Provides standard CRUD operations as well as custom queries for
 * retrieving user accounts by role, email, and full name.
 * </p>
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Finds all user accounts with the specified role.
     *
     * @param roles the {@link Roles} value to filter by
     * @return a list of {@link UserAccount} entities with the given role
     */
    List<UserAccount> findByRole(Roles roles);


    /**
     * Finds a user account by its email.
     *
     * @param email the {@link Email} value object
     * @return an {@link Optional} containing the {@link UserAccount} if found, or empty otherwise
     */
    Optional<UserAccount> findByEmail(Email email);

    /**
     * Finds a user account by its full name.
     *
     * @param fullName the {@link FullName} value object
     * @return an {@link Optional} containing the {@link UserAccount} if found, or empty otherwise
     */
    Optional<UserAccount> findByFullName(FullName fullName);
}