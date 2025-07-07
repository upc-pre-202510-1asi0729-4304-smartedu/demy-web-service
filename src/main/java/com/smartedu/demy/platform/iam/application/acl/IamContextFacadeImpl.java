package com.smartedu.demy.platform.iam.application.acl;

import com.smartedu.demy.platform.iam.domain.model.queries.GetTeacherByFullNameQuery;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link IamContextFacade} interface.
 * Provides an abstraction layer for IAM-related operations exposed to external bounded contexts.
 */
@Service
public class IamContextFacadeImpl implements IamContextFacade {
    private final UserAccountQueryService userAccountQueryService;

    /**
     * Constructs the IAM context facade implementation.
     *
     * @param userAccountQueryService the service used to query user account data.
     */
    public IamContextFacadeImpl(UserAccountQueryService userAccountQueryService) {
        this.userAccountQueryService = userAccountQueryService;
    }

    /**
     * Fetches the ID of a teacher by their full name.
     * If the user does not exist or is not a teacher, returns {@code 0L}.
     *
     * @param firstName the first name of the teacher.
     * @param lastName  the last name of the teacher.
     * @return the ID of the teacher if found and valid; {@code 0L} otherwise.
     * @implNote This method relies on {@link UserAccountQueryService} to find the user.
     */
    @Override
    public Long fetchTeacherIdByFullName(String firstName, String lastName) {
        FullName fullName = new FullName(firstName, lastName);
        var query = new GetTeacherByFullNameQuery(fullName);
        var teacher = userAccountQueryService.handle(query);
        if (teacher.isEmpty() || teacher.get().getRole() != Roles.TEACHER) {
            return 0L;
        }
        return teacher.get().getId();
    }
}



