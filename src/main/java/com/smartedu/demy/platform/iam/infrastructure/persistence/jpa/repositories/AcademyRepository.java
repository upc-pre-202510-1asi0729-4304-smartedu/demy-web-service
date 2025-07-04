package com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository interface for accessing and managing {@link Academy} entities.
 * <p>
 * Provides basic CRUD operations and custom queries related to academies.
 * </p>
 */
public interface AcademyRepository extends JpaRepository<Academy, Long> {

    /**
     * Retrieves an {@link Academy} associated with a specific user ID.
     *
     * @param userId the unique identifier of the user who created the academy
     * @return an {@link Optional} containing the {@link Academy} if found, or empty otherwise
     */
    Optional<Academy> findByUserId_Value(Long userId);

}
