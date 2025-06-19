package com.smartedu.demy.platform.scheduling.application.internal.queryservices;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Course;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllCoursesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetCourseByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.CourseQueryService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseRepository courseRepository;

    public CourseQueryServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> handle(GetAllCoursesQuery query) {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> handle(GetCourseByIdQuery query) {
        return courseRepository.findById(query.courseId());
    }
}