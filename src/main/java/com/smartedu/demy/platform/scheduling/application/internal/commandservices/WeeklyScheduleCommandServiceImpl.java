package com.smartedu.demy.platform.scheduling.application.internal.commandservices;

import com.smartedu.demy.platform.scheduling.application.internal.outboundservices.acl.ExternalIamService;
import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.commands.*;
import com.smartedu.demy.platform.scheduling.domain.model.entities.Schedule;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.DayOfWeek;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleCommandService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.ScheduleRepository;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.WeeklyScheduleRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeeklyScheduleCommandServiceImpl implements WeeklyScheduleCommandService {

    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final ExternalIamService externalIamService;
    private final ScheduleRepository scheduleRepository;

    public WeeklyScheduleCommandServiceImpl(WeeklyScheduleRepository weeklyScheduleRepository, ExternalIamService externalIamService, ScheduleRepository scheduleRepository) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.externalIamService = externalIamService;
        this.scheduleRepository = scheduleRepository;
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

        // Obtener el UserId del profesor usando el servicio externo
        UserId teacherId = externalIamService
                .fetchTeacherIdByFullName(command.teacherFirstName(), command.teacherLastName())
                .orElseThrow(() -> new IllegalArgumentException("No teacher with that fullname was found"));

        weeklySchedule.addSchedule(
                command.startTime(),
                command.endTime(),
                dayOfWeek,
                command.courseId(),
                command.classroomId(),
                teacherId.value()
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

    @Override
    public void handle(DeleteWeeklyScheduleCommand command) {
        if (!weeklyScheduleRepository.existsById(command.weeklyScheduleId())) {
            throw new IllegalArgumentException("WeeklySchedule with id " + command.weeklyScheduleId() + " not found");
        }
        try {
            weeklyScheduleRepository.deleteById(command.weeklyScheduleId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting weekly schedule: " + e.getMessage(), e);
        }

    }

    @Override
    public Optional<Schedule> handle(UpdateScheduleCommand command) {
        var scheduleOpt = scheduleRepository.findById(command.scheduleId());
        if (scheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Schedule with id " + command.scheduleId() + " not found");
        }
        var schedule = scheduleOpt.get();
        schedule.updateSchedule(
            command.classroomId(),
            command.startTime(),
            command.endTime(),
            DayOfWeek.valueOf(command.dayOfWeek().toUpperCase())
        );
        scheduleRepository.save(schedule);
        return Optional.of(schedule);
    }
}
