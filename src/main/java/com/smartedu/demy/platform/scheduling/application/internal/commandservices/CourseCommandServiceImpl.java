package com.smartedu.demy.platform.scheduling.application.internal.commandservices;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Course;
import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.DeleteCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.services.CourseCommandService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseCommandServiceImpl implements CourseCommandService {

    private final CourseRepository courseRepository;

    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Long handle(CreateCourseCommand command) {
        if (courseRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Course with code " + command.name() + " already exists");
        }

        var course = new Course(command);
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving course: " + e.getMessage(), e);
        }
        return course.getId();
    }

    @Override
    public Optional<Course> handle(UpdateCourseCommand command) {

        if (courseRepository.existsByNameAndIdNot(command.name(), command.courseId())) {
            throw new IllegalArgumentException("Course with name " + command.name() + " already exists");
        }

        var result = courseRepository.findById(command.courseId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Course with id " + command.courseId() + " not found");
        }

        var courseToUpdate = result.get();
        try {
            var updatedCourse = courseRepository.save(courseToUpdate.updateCourse(command));
            return Optional.of(updatedCourse);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating course: " + e.getMessage());
        }

    }

    @Override
    public void handle(DeleteCourseCommand command) {
        if (!courseRepository.existsById(command.courseId())) {
            throw new IllegalArgumentException("Course with id " + command.courseId() + " not found");
        }
        try {
            courseRepository.deleteById(command.courseId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting course: " + e.getMessage(), e);
        }

    }
}
