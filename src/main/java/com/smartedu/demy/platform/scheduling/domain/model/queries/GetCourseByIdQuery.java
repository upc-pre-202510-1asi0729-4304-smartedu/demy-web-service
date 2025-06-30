package com.smartedu.demy.platform.scheduling.domain.model.queries;

import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.CourseId;

public record GetCourseByIdQuery(Long courseId) {

    public GetCourseByIdQuery {
        if(courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID is required and must be a positive number.");
        }
    }
}