package com.smartedu.demy.platform.scheduling.interfaces.rest.transform;

import com.smartedu.demy.platform.scheduling.domain.model.entities.Schedule;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.ScheduleResource;

public class ScheduleResourceFromEntityAssembler {
    /**
     * Converts a Schedule entity to a ScheduleResource
     * @param entity the Schedule entity
     * @return the ScheduleResource
     */
    public static ScheduleResource toResourceFromEntity(Schedule entity) {
        return new ScheduleResource(
                entity.getId(),
                entity.getTimeRange().startTime().toString(),
                entity.getTimeRange().endTime().toString(),
                entity.getDayOfWeek().name(),
                entity.getCourseId().id(),
                entity.getClassroomId().id()
        );
    }
}