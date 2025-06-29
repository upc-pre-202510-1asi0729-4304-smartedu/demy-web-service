package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record UpdateClassroomCommand(
        Long classroomId,
        String code,
        Integer capacity,
        String campus
) {
    public UpdateClassroomCommand {
        if(classroomId == null || classroomId <= 0) {
            throw new IllegalArgumentException("Classroom ID must be a positive number.");
        }
        if(code == null || code.isBlank()) {
            throw new IllegalArgumentException("Classroom code must not be null or blank.");
        }
        if(capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("Classroom capacity must be a positive number.");
        }
        if(campus == null || campus.isBlank()) {
            throw new IllegalArgumentException("Campus must not be null or blank.");
        }
    }
}
