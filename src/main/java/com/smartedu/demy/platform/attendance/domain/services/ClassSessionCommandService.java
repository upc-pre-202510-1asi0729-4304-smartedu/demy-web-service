package com.smartedu.demy.platform.attendance.domain.services;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.commands.CreateClassSessionCommand;

import java.util.Optional;

/**
 * @name ClassSessionCommandService
 * @summary
 * This interface represents the service to handle class session commands.
 */
public interface ClassSessionCommandService {
    /**
     * Handles the create class session command.
     * @param command The create class session command.
     * @return The created class session.
     *
     * @throws IllegalArgumentException If courseId, date or attendance is null or empty
     * @see CreateClassSessionCommand
     */
    Optional<ClassSession> handle (CreateClassSessionCommand command);
}
