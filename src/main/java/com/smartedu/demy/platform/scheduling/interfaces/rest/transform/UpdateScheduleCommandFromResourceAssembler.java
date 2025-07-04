package com.smartedu.demy.platform.scheduling.interfaces.rest.transform;

import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateScheduleCommand;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.UpdateScheduleResource;

public class UpdateScheduleCommandFromResourceAssembler {
    public static UpdateScheduleCommand toCommandFromResource(Long scheduleId, UpdateScheduleResource resource) {
        return new UpdateScheduleCommand(
            scheduleId,
            resource.classroomId(),
            resource.startTime(),
            resource.endTime(),
            resource.dayOfWeek()
        );
    }
}
