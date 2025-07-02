package com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.AcademicPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Long> {
    boolean existsByPeriodName (String periodName);
    Optional<AcademicPeriod> findByPeriodName (String periodName);
    boolean existsByPeriodNameAndIdIsNot(String periodName, Long id);
}
