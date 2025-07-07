package com.smartedu.demy.platform.scheduling.application.internal.queryservices;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Course;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllCoursesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetCourseByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.CourseQueryService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Course Query Service Implementation
 * <p>This class implements the course query service interface and provides the business logic for handling course queries such as retrieving all courses and finding courses by ID.</p>
 */
@Service
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseRepository courseRepository;

    /**
     * Constructor that initializes the service with the required repository.
     * @param courseRepository The course repository.
     */
    public CourseQueryServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * This method is used to handle retrieving all courses.
     * @param query The get all courses query.
     * @return A list of all courses in the system.
     * @see GetAllCoursesQuery
     * @see Course
     */
    @Override
    public List<Course> handle(GetAllCoursesQuery query) {
        return courseRepository.findAll();
    }

    /**
     * This method is used to handle retrieving a course by its ID.
     * @param query The get course by ID query containing the course ID.
     * @return An optional with the course if found, otherwise an empty optional.
     * @see GetCourseByIdQuery
     * @see Course
     */
    @Override
    public Optional<Course> handle(GetCourseByIdQuery query) {
        return courseRepository.findById(query.courseId());
    }
}