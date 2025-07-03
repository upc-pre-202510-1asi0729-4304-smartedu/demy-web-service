package com.smartedu.demy.platform.iam.application.acl;

import com.smartedu.demy.platform.iam.domain.model.queries.GetTeacherByFullNameQuery;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

@Service
public class IamContextFacadeImpl implements IamContextFacade {
    private final UserAccountQueryService userAccountQueryService;

    public IamContextFacadeImpl(UserAccountQueryService userAccountQueryService) {
        this.userAccountQueryService = userAccountQueryService;
    }

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



