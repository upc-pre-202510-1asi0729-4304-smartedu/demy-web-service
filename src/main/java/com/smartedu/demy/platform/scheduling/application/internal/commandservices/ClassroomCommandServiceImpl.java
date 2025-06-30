package com.smartedu.demy.platform.scheduling.application.internal.commandservices;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Classroom;
import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.DeleteClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.services.ClassroomCommandService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassroomCommandServiceImpl implements ClassroomCommandService {

    private final ClassroomRepository classroomRepository;

    public ClassroomCommandServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Long handle(CreateClassroomCommand command) {
        if (classroomRepository.existsByCode(command.code())) {
            throw new IllegalArgumentException("Classroom with code " + command.code() + " already exists");
        }

        var classroom = new Classroom(command);
        try {
            classroomRepository.save(classroom);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving classroom: %s".formatted(e.getMessage()));
        }
        return classroom.getId();
    }

    @Override
    public Optional<Classroom> handle(UpdateClassroomCommand command) {

        if (classroomRepository.existsByCodeAndIdNot(command.code(), command.classroomId())) {
            throw new IllegalArgumentException("Classroom with code " + command.code() + " already exists");
        }

        var result = classroomRepository.findById(command.classroomId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Classroom with id " + command.classroomId() + " not found");
        }

        var classroomToUpdate = result.get();
        try {
            var updatedClassroom = classroomRepository.save(classroomToUpdate.updateClassroom(command));
            return Optional.of(updatedClassroom);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating classroom: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteClassroomCommand command) {
        if (!classroomRepository.existsById(command.classroomId())) {
            throw new IllegalArgumentException("Classroom with id " + command.classroomId() + " not found");
        }

        try {
            classroomRepository.deleteById(command.classroomId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting classroom: " + e.getMessage(), e);
        }
    }
}
