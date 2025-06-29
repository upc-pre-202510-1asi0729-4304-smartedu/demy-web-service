package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record DeleteCourseCommand(Long courseId) {

    public DeleteCourseCommand {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("courseId cannot be null or less than 1");
        }
    }
}