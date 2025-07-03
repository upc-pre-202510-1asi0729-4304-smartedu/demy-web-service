package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record CreateWeeklyScheduleCommand(String name) {

    public CreateWeeklyScheduleCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
    }

}