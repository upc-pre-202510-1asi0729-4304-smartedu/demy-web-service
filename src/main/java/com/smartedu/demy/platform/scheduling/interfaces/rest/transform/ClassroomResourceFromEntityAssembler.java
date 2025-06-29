package com.smartedu.demy.platform.scheduling.interfaces.rest.transform;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Classroom;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.ClassroomResource;

public class ClassroomResourceFromEntityAssembler {
    /**
     * Converts a Classroom entity to a ClassroomResource
     * @param entity the Classroom entity
     * @return the ClassroomResource
     */
    public static ClassroomResource toResourceFromEntity(Classroom entity) {
        return new ClassroomResource(
                entity.getId(),
                entity.getCode(),
                entity.getCapacity(),
                entity.getCampus()
        );
    }
}