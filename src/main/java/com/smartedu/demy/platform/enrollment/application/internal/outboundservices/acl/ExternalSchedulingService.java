package com.smartedu.demy.platform.enrollment.application.internal.outboundservices.acl;

import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.WeeklyScheduleId;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ExternalSchedulingService {
    private final SchedulingContextFacade schedulingContextFacade;

    public ExternalSchedulingService(SchedulingContextFacade schedulingContextFacade) {
        this.schedulingContextFacade = schedulingContextFacade;
    }

    public Optional<WeeklyScheduleId> fetchWeeklyScheduleByName(String weeklyScheduleName) {
        var weeklyScheduleId = schedulingContextFacade.fetchWeeklyScheduleIdByName(weeklyScheduleName);
        return weeklyScheduleId == 0L ? Optional.empty() : Optional.of(weeklyScheduleId);
    }
}
