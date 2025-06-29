package com.smartedu.demy.platform.scheduling.domain.services;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Course;
import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.DeleteCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateCourseCommand;

import java.util.Optional;

public interface CourseCommandService {

    Long handle(CreateCourseCommand command);

    Optional<Course> handle(UpdateCourseCommand command);

    void handle(DeleteCourseCommand command);
}