package com.smartedu.demy.platform.scheduling.application.internal.queryservices;


import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllWeeklySchedulesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleQueryService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.WeeklyScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyScheduleQueryServiceImpl implements WeeklyScheduleQueryService {

    private final WeeklyScheduleRepository weeklyScheduleRepository;

    public WeeklyScheduleQueryServiceImpl(WeeklyScheduleRepository weeklyScheduleRepository) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
    }

    @Override
    public List<WeeklySchedule> handle(GetAllWeeklySchedulesQuery query) {
        return weeklyScheduleRepository.findAll();
    }

    @Override
    public Optional<WeeklySchedule> handle(GetWeeklyScheduleByIdQuery query) {
        return weeklyScheduleRepository.findById(query.weeklyScheduleId());
    }
}