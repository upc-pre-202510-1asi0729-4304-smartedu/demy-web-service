package com.smartedu.demy.platform.scheduling.domain.services;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllWeeklySchedulesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface WeeklyScheduleQueryService {

    List<WeeklySchedule> handle(GetAllWeeklySchedulesQuery query);

    Optional<WeeklySchedule> handle(GetWeeklyScheduleByIdQuery query);

    Optional<WeeklySchedule> handle(GetWeeklyScheduleByNameQuery query);


}