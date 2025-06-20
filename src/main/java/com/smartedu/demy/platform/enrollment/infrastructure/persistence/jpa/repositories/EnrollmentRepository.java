package com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.StudentId;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PeriodId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * Encuentra todas las matrículas por el ID del estudiante.
     */
    List<Enrollment> findAllByStudentId(StudentId studentId);

    /**
     * Encuentra todas las matrículas por el ID del periodo.
     */
    List<Enrollment> findAllByPeriodId(PeriodId periodId);

    /**
     * Encuentra una matrícula por ID de estudiante y periodo.
     */
    Optional<Enrollment> findByStudentIdAndPeriodId(StudentId studentId, PeriodId periodId);
}
