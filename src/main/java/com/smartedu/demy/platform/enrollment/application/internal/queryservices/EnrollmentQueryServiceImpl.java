package com.smartedu.demy.platform.enrollment.application.internal.queryservices;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllEnrollmentsByStudentDniQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllEnrollmentsByStudentIdQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllEnrollmentsQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetEnrollmentByIdQuery;
import com.smartedu.demy.platform.enrollment.domain.services.EnrollmentQueryService;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.StudentRepository;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.StudentId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentQueryServiceImpl implements EnrollmentQueryService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public EnrollmentQueryServiceImpl(EnrollmentRepository enrollmentRepository,
                                      StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Enrollment> handle(GetAllEnrollmentsByStudentIdQuery query) {
        return enrollmentRepository.findAllByStudentId(new StudentId(query.studentId()));
    }

    @Override
    public List<Enrollment> handle(GetAllEnrollmentsQuery query) {
        return enrollmentRepository.findAll();
    }

    @Override
    public Optional<Enrollment> handle(GetEnrollmentByIdQuery query) {
        return enrollmentRepository.findById(query.enrollmentId());
    }

    @Override
    public List<Enrollment> handle(GetAllEnrollmentsByStudentDniQuery query) {
        var student = studentRepository.findByDni_Dni(query.dni());
        if (student.isEmpty()) {
            throw new IllegalArgumentException("Student with DNI %s not found".formatted(query.dni()));
        }
        return enrollmentRepository.findAllByStudentId(new StudentId(student.get().getId()));
    }
}
