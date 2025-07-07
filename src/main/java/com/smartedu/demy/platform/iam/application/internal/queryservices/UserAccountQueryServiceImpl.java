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

/**
 * Implementation of the {@link UserAccountQueryService} interface.
 * Provides query operations related to user accounts, such as retrieving admins,
 * teachers, or specific users by ID or full name.
 */
@Service
public class UserAccountQueryServiceImpl implements UserAccountQueryService {

    private final UserAccountRepository userAccountRepository;

    /**
     * Constructs the query service with the required repository.
     *
     * @param userAccountRepository the repository used to access user account data.
     */
    public UserAccountQueryServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * Retrieves all user accounts with the ADMIN role.
     *
     * @param query the query object (not used, for CQRS pattern compatibility).
     * @return a list of user accounts with role ADMIN.
     */
    @Override
    public List<UserAccount> handle(GetAllAdminsQuery query) {
        return userAccountRepository.findByRole(Roles.ADMIN);
    }

    /**
     * Retrieves all user accounts with the TEACHER role.
     *
     * @param query the query object (not used, for CQRS pattern compatibility).
     * @return a list of user accounts with role TEACHER.
     */
    @Override
    public List<UserAccount> handle(GetAllTeachersQuery query) {
        return userAccountRepository.findByRole(Roles.TEACHER);
    }

    /**
     * Retrieves a user account by its unique identifier.
     *
     * @param query the query containing the user ID.
     * @return an {@link Optional} containing the user account if found; otherwise, empty.
     */
    @Override
    public Optional<UserAccount> handle(GetUserAccountByIdQuery query) {
        return userAccountRepository.findById(query.id());
    }

    /**
     * Retrieves a teacher account based on full name.
     *
     * @param query the query containing the full name of the teacher.
     * @return an {@link Optional} containing the matching teacher account if found; otherwise, empty.
     */
    @Override
    public Optional<UserAccount> handle(GetTeacherByFullNameQuery query) {
        return userAccountRepository.findByFullName(query.fullname());
    }
}