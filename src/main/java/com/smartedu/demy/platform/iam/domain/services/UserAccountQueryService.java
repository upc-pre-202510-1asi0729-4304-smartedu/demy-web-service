package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllAdminsQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllTeachersQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetTeacherByFullNameQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetUserAccountByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling user account query operations.
 * Provides read-only access to user account data based on various criteria.
 */
public interface UserAccountQueryService {

    /**
     * Retrieves all user accounts with the ADMIN role.
     *
     * @param query the query object (typically unused, but included for CQRS consistency).
     * @return a list of {@link UserAccount} objects with ADMIN role.
     */
    List<UserAccount> handle(GetAllAdminsQuery query);

    /**
     * Retrieves all user accounts with the TEACHER role.
     *
     * @param query the query object (typically unused, but included for CQRS consistency).
     * @return a list of {@link UserAccount} objects with TEACHER role.
     */
    List<UserAccount> handle(GetAllTeachersQuery query);

    /**
     * Retrieves a user account by its unique identifier.
     *
     * @param query the query containing the user account ID.
     * @return an {@link Optional} containing the {@link UserAccount} if found, or empty otherwise.
     */
    Optional<UserAccount> handle(GetUserAccountByIdQuery query);

    /**
     * Retrieves a teacher account matching the provided full name.
     *
     * @param query the query containing the teacher's full name.
     * @return an {@link Optional} containing the matching {@link UserAccount} if found, or empty otherwise.
     */
    Optional<UserAccount> handle(GetTeacherByFullNameQuery query);
}