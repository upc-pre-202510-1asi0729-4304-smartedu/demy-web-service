package com.smartedu.demy.platform.scheduling.interfaces.rest.resources;

public record ClassroomResource(
        Long id,
        String code,
        Integer capacity,
        String campus
) {
}