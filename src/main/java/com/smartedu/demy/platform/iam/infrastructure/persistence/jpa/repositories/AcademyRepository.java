package com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademyRepository extends JpaRepository<Academy, Long> {
    Optional<Academy> findByUserId_Value(Long userId);

}
