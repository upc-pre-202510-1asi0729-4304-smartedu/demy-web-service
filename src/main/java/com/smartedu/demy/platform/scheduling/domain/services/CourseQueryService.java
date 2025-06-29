package com.smartedu.demy.platform.scheduling.domain.services;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Course;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllCoursesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetCourseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CourseQueryService {

    List<Course> handle(GetAllCoursesQuery query);

    Optional<Course> handle(GetCourseByIdQuery query);
}