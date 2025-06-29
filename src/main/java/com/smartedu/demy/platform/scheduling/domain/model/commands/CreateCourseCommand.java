package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record CreateCourseCommand(String name, String code, String description) {

    public CreateCourseCommand{
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code must not be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must not be null or blank");
        }
    }
}