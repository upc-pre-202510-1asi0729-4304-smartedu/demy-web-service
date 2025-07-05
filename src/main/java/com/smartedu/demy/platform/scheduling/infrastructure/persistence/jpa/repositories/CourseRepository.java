package com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Course} entities.
 * Provides methods for performing CRUD operations and additional queries related to courses.
 *
 * Extends {@link JpaRepository} to inherit standard CRUD operations.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Finds a course by its unique name.
     *
     * @param name The name of the course.
     * @return An {@link Optional} containing the found course, or an empty {@link Optional} if no course is found.
     */
    Optional<Course> findByName(String name);

    /**
     * Checks if a course with the specified name already exists.
     *
     * @param name The name of the course to check.
     * @return {@code true} if a course with the given name exists, otherwise {@code false}.
     */
    boolean existsByName(String name);

    /**
     * Checks if a course with the specified name exists, excluding a specific course by its ID.
     *
     * @param name The name of the course to check.
     * @param id The ID of the course to exclude from the check.
     * @return {@code true} if a course with the given name exists and its ID is not the specified one, otherwise {@code false}.
     */
    boolean existsByNameAndIdNot(String name, Long id);
}
