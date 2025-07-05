package com.smartedu.demy.platform.enrollment.application.acl;

import com.smartedu.demy.platform.enrollment.domain.model.queries.GetStudentByDniQuery;
import com.smartedu.demy.platform.enrollment.domain.services.StudentQueryService;
import com.smartedu.demy.platform.enrollment.interfaces.acl.EnrollmentsContextFacade;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentsContextFacadeImpl implements EnrollmentsContextFacade {
    private final StudentQueryService studentQueryService;

    public EnrollmentsContextFacadeImpl(StudentQueryService studentQueryService) {
        this.studentQueryService = studentQueryService;
    }

    public String fetchStudentFullNameByDni(String dni) {
        var getStudentByDniQuery = new GetStudentByDniQuery(dni);
        var student = studentQueryService.handle(getStudentByDniQuery);
        return student.map(value -> value.getFullName().getFullName()).orElse("");
    }
}
