package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record CreateClassroomCommand(String code, Integer capacity, String campus) {

    public CreateClassroomCommand {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code must not be null or blank");
        }
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        if (campus == null || campus.isBlank()) {
            throw new IllegalArgumentException("Campus must not be null or blank");
        }
    }
}
