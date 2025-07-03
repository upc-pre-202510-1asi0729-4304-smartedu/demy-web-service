package com.smartedu.demy.platform.scheduling.domain.model.aggregates;

import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateWeeklyScheduleCommand;
import com.smartedu.demy.platform.scheduling.domain.model.entities.Schedule;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.DayOfWeek;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class WeeklySchedule extends AuditableAbstractAggregateRoot<WeeklySchedule> {

    private String name;

    @OneToMany(mappedBy = "weeklySchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Schedule> schedules;

    /**
     * Default constructor
     */
    public WeeklySchedule() {
        this.name = "";
        this.schedules = new ArrayList<>();
    }

    /**
     * Constructor with name
     * @param name Weekly schedule name
     */
    public WeeklySchedule(String name) {
        this.name = name;
        this.schedules = new ArrayList<>();
    }

    /**
     * Constructor with command
     * @param command Create weekly schedule command
     */
    public WeeklySchedule(CreateWeeklyScheduleCommand command) {
        this.name = command.name();
        this.schedules = new ArrayList<>();
    }

    /**
     * Update name
     * @param name New name
     */
    public void updateName(String name) {
        this.name = name;
    }

    /**
     * Add schedule
     * @param schedule Schedule to add
     */
    public void addSchedule(Schedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule must not be null");
        }

        if (hasConflictWith(schedule)) {
            throw new IllegalArgumentException("Schedule conflicts with existing schedule in the same classroom and time slot");
        }

        schedules.add(schedule);
    }

//    /**
//     * Add schedule with parameters
//     * @param startTime Start time
//     * @param endTime End time
//     * @param dayOfWeek Day of week
//     * @param courseId Course ID
//     * @param classroomId Classroom ID
//     */
//    public void addSchedule(String startTime, String endTime, DayOfWeek dayOfWeek, Long courseId, Long classroomId) {
//        var schedule = new Schedule(startTime, endTime, dayOfWeek, courseId, classroomId);
//        schedules.add(schedule);
//    }

    /**
     * Add schedule with parameters
     * @param startTime Start time
     * @param endTime End time
     * @param dayOfWeek Day of week
     * @param courseId Course ID
     * @param classroomId Classroom ID
     * @param teacherId Teacher ID
     */
    public void addSchedule(String startTime, String endTime, DayOfWeek dayOfWeek, Long courseId, Long classroomId, Long teacherId) {
        var schedule = new Schedule(startTime, endTime, dayOfWeek, courseId, classroomId, teacherId);

        schedule.setWeeklySchedule(this);
        schedules.add(schedule);
    }

    /**
     * Remove schedule
     * @param scheduleId Schedule ID to remove
     */
    public void removeSchedule(Long scheduleId) {
        schedules.removeIf(s -> s.getId().equals(scheduleId));
    }

    /**
     * Check if has conflicts
     * @return True if has conflicts, false otherwise
     */
    public boolean hasConflicts() {
        for (int i = 0; i < schedules.size(); i++) {
            for (int j = i + 1; j < schedules.size(); j++) {
                if (schedules.get(i).conflictsWith(schedules.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get conflicts
     * @return List of conflicting schedule pairs
     */
    public List<ScheduleConflict> getConflicts() {
        var conflicts = new ArrayList<ScheduleConflict>();

        for (int i = 0; i < schedules.size(); i++) {
            for (int j = i + 1; j < schedules.size(); j++) {
                if (schedules.get(i).conflictsWith(schedules.get(j))) {
                    conflicts.add(new ScheduleConflict(schedules.get(i), schedules.get(j)));
                }
            }
        }

        return conflicts;
    }


    private boolean hasConflictWith(Schedule newSchedule) {
        return schedules.stream().anyMatch(existingSchedule -> existingSchedule.conflictsWith(newSchedule));
    }

    /**
     * Schedule Conflict record
     */
    public record ScheduleConflict(Schedule schedule1, Schedule schedule2) {}
}