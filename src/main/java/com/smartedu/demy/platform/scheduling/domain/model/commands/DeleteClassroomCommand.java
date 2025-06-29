package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record DeleteClassroomCommand(Long classroomId) {

    public DeleteClassroomCommand {
        if (classroomId == null || classroomId <= 0) {
            throw new IllegalArgumentException("classroomId cannot be null or less than 1");
        }
    }
}