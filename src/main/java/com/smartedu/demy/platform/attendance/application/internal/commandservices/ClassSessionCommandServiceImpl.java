package com.smartedu.demy.platform.attendance.application.internal.commandservices;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.commands.CreateClassSessionCommand;
import com.smartedu.demy.platform.attendance.domain.services.ClassSessionCommandService;
import com.smartedu.demy.platform.attendance.infrastructure.persistence.jpa.ClassSessionRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ClassSessionCommandService Implementation
 *
 * @summary
 * Implementation of the ClassSessionService interface.
 * It is responsible for handling class sessions commands.
 *
 * @since 1.0
 */
@Service
public class ClassSessionCommandServiceImpl
        implements ClassSessionCommandService {
    private final ClassSessionRepository classSessionRepository;
    public ClassSessionCommandServiceImpl(ClassSessionRepository classSessionrepository) {
        this.classSessionRepository = classSessionrepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ClassSession> handle (CreateClassSessionCommand command) {
        if
        (classSessionRepository.existsByCourseIdAndDate(command.courseId(), command.date()))
            throw new IllegalArgumentException("Class session wit the same course ID and date already exists");
        var classSession = new ClassSession(command);
        var createdClassSession =  classSessionRepository.save(classSession);
        return Optional.of(createdClassSession);

    }
}
