package com.smartedu.demy.platform.scheduling.domain.services;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.commands.AddScheduleToWeeklyCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateWeeklyScheduleCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.RemoveScheduleFromWeeklyCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateWeeklyScheduleNameCommand;

import java.util.Optional;

public interface WeeklyScheduleCommandService {

    Long handle(CreateWeeklyScheduleCommand command);

    Optional<WeeklySchedule> handle(UpdateWeeklyScheduleNameCommand command);

    Optional<WeeklySchedule> handle(AddScheduleToWeeklyCommand command);

    Optional<WeeklySchedule> handle(RemoveScheduleFromWeeklyCommand command);
}