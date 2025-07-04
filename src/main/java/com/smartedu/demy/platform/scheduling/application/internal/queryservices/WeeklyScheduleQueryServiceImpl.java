package com.smartedu.demy.platform.scheduling.application.internal.queryservices;


import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.entities.Schedule;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllWeeklySchedulesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetSchedulesByTeacherIdQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByNameQuery;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleQueryService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.WeeklyScheduleRepository;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyScheduleQueryServiceImpl implements WeeklyScheduleQueryService {

    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final ScheduleRepository scheduleRepository;


    public WeeklyScheduleQueryServiceImpl(WeeklyScheduleRepository weeklyScheduleRepository, ScheduleRepository scheduleRepository) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.scheduleRepository= scheduleRepository;
    }

    @Override
    public List<WeeklySchedule> handle(GetAllWeeklySchedulesQuery query) {
        return weeklyScheduleRepository.findAll();
    }

    @Override
    public Optional<WeeklySchedule> handle(GetWeeklyScheduleByIdQuery query) {
        return weeklyScheduleRepository.findById(query.weeklyScheduleId());
    }

    @Override
    public Optional<WeeklySchedule> handle(GetWeeklyScheduleByNameQuery query) {
        return weeklyScheduleRepository.findByName(query.name());
    }

    @Override
    public List<Schedule> handle(GetSchedulesByTeacherIdQuery query){
        return scheduleRepository.findByTeacherId(query.teacherId());
    }


}