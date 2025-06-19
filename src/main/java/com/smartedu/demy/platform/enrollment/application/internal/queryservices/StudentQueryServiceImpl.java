package com.smartedu.demy.platform.enrollment.application.internal.queryservices;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Student;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllStudentsQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetStudentByDniQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetStudentByIdQuery;
import com.smartedu.demy.platform.enrollment.domain.services.StudentQueryService;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentQueryServiceImpl implements StudentQueryService {

    private final StudentRepository studentRepository;

    public StudentQueryServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> handle(GetStudentByIdQuery query) {
        return studentRepository.findById(query.studentId());
    }

    @Override
    public List<Student> handle(GetAllStudentsQuery query) {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> handle(GetStudentByDniQuery query) {
        return studentRepository.findByDni_Dni(query.dni());
    }
}
