package com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Classroom} entities.
 * Provides methods for performing CRUD operations and additional queries related to classrooms.
 *
 * Extends {@link JpaRepository} to inherit standard CRUD operations.
 */
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    /**
     * Finds a classroom by its unique code.
     *
     * @param code The unique code of the classroom.
     * @return An {@link Optional} containing the found classroom, or an empty {@link Optional} if no classroom is found.
     */
    Optional<Classroom> findByCode(String code);

    /**
     * Checks if a classroom with the specified code already exists.
     *
     * @param code The code of the classroom to check.
     * @return {@code true} if a classroom with the given code exists, otherwise {@code false}.
     */
    boolean existsByCode(String code);

    /**
     * Checks if a classroom with the specified code exists, excluding a specific classroom by its ID.
     *
     * @param code The code of the classroom to check.
     * @param id The ID of the classroom to exclude from the check.
     * @return {@code true} if a classroom with the given code exists and its ID is not the specified one, otherwise {@code false}.
     */
    boolean existsByCodeAndIdNot(String code, Long id);
}
