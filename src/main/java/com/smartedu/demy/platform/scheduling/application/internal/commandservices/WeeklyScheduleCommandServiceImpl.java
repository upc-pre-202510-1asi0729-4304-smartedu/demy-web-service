package com.smartedu.demy.platform.scheduling.application.internal.commandservices;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.commands.AddScheduleToWeeklyCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateWeeklyScheduleCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.RemoveScheduleFromWeeklyCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateWeeklyScheduleNameCommand;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.DayOfWeek;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleCommandService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.WeeklyScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeeklyScheduleCommandServiceImpl implements WeeklyScheduleCommandService {

    private final WeeklyScheduleRepository weeklyScheduleRepository;

    public WeeklyScheduleCommandServiceImpl(WeeklyScheduleRepository weeklyScheduleRepository) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
    }

    @Override
    public Long handle(CreateWeeklyScheduleCommand command) {
        if (weeklyScheduleRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Weekly schedule with name " + command.name() + " already exists");
        }

        var weeklySchedule = new WeeklySchedule(command);
        weeklyScheduleRepository.save(weeklySchedule);
        return weeklySchedule.getId();
    }

    @Override
    public Optional<WeeklySchedule> handle(UpdateWeeklyScheduleNameCommand command) {
        var weeklyScheduleOpt = weeklyScheduleRepository.findById(command.weeklyScheduleId());
        if (weeklyScheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Weekly schedule with id " + command.weeklyScheduleId() + " not found");
        }

        if (weeklyScheduleRepository.existsByNameAndIdNot(command.name(), command.weeklyScheduleId())) {
            throw new IllegalArgumentException("Weekly schedule with name " + command.name() + " already exists");
        }

        var weeklySchedule = weeklyScheduleOpt.get();
        weeklySchedule.updateName(command.name());
        weeklyScheduleRepository.save(weeklySchedule);
        return Optional.of(weeklySchedule);
    }

    @Override
    public Optional<WeeklySchedule> handle(AddScheduleToWeeklyCommand command) {
        var weeklyScheduleOpt = weeklyScheduleRepository.findById(command.weeklyScheduleId());
        if (weeklyScheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Weekly schedule with id " + command.weeklyScheduleId() + " not found");
        }

        var weeklySchedule = weeklyScheduleOpt.get();
        var dayOfWeek = DayOfWeek.valueOf(command.dayOfWeek().toUpperCase());

        weeklySchedule.addSchedule(
                command.startTime(),
                command.endTime(),
                dayOfWeek,
                command.courseId(),
                command.classroomId()
        );

        weeklyScheduleRepository.save(weeklySchedule);
        return Optional.of(weeklySchedule);
    }

    @Override
    public Optional<WeeklySchedule> handle(RemoveScheduleFromWeeklyCommand command) {
        var weeklyScheduleOpt = weeklyScheduleRepository.findById(command.weeklyScheduleId());
        if (weeklyScheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Weekly schedule with id " + command.weeklyScheduleId() + " not found");
        }

        var weeklySchedule = weeklyScheduleOpt.get();
        weeklySchedule.removeSchedule(command.scheduleId());
        weeklyScheduleRepository.save(weeklySchedule);
        return Optional.of(weeklySchedule);
    }
}
