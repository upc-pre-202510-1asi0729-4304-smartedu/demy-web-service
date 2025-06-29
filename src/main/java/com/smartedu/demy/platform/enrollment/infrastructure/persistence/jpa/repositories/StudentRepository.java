package com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsStudentByDni_Dni(String dni);
    Optional<Student> findByDni_Dni(String dni);
}