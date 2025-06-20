package com.smartedu.demy.platform.enrollment.domain.services;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Student;
import com.smartedu.demy.platform.enrollment.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface StudentQueryService {
    Optional<Student> handle(GetStudentByIdQuery query);
    List<Student> handle(GetAllStudentsQuery query);
    Optional<Student> handle(GetStudentByDniQuery query);
}
