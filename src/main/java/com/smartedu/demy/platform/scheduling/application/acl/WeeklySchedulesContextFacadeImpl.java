package com.smartedu.demy.platform.scheduling.application.acl;

import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByNameQuery;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleQueryService;
import com.smartedu.demy.platform.scheduling.interfaces.acl.WeeklySchedulesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class WeeklySchedulesContextFacadeImpl implements WeeklySchedulesContextFacade {

    private final WeeklyScheduleQueryService weeklyScheduleQueryService;

    public WeeklySchedulesContextFacadeImpl(WeeklyScheduleQueryService weeklyScheduleQueryService) {
        this.weeklyScheduleQueryService = weeklyScheduleQueryService;
    }

    @Override
    public Long fetchWeeklyScheduleIdByName(String name) {
        var getWeeklyScheduleByNameQuery = new GetWeeklyScheduleByNameQuery(name);
        var weeklySchedule = weeklyScheduleQueryService.handle(getWeeklyScheduleByNameQuery);
        return weeklySchedule.isEmpty() ? Long.valueOf(0L) : weeklySchedule.get().getId();
    }
}