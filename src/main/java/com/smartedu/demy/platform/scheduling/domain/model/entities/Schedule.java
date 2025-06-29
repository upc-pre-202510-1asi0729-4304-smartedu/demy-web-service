package com.smartedu.demy.platform.scheduling.domain.model.entities;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.ClassroomId;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.CourseId;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.DayOfWeek;
import com.smartedu.demy.platform.scheduling.domain.model.valueobjects.TimeRange;
import com.smartedu.demy.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Schedule extends AuditableModel {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startTime", column = @Column(name = "start_time")),
            @AttributeOverride(name = "endTime", column = @Column(name = "end_time"))
    })
    private TimeRange timeRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "course_id"))
    })
    private CourseId courseId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "classroom_id"))
    })
    private ClassroomId classroomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private WeeklySchedule weeklySchedule;

    /**
     * Default constructor
     */
    public Schedule() {}

    /**
     * Constructor with TimeRange
     * @param timeRange Time range
     * @param dayOfWeek Day of week
     * @param courseId Course ID
     * @param classroomId Classroom ID
     */
    public Schedule(TimeRange timeRange, DayOfWeek dayOfWeek, CourseId courseId, ClassroomId classroomId) {
        this.timeRange = timeRange;
        this.dayOfWeek = dayOfWeek;
        this.courseId = courseId;
        this.classroomId = classroomId;
        validateSchedule();
    }

    /**
     * Constructor with string times
     * @param startTime Start time
     * @param endTime End time
     * @param dayOfWeek Day of week
     * @param courseId Course ID
     * @param classroomId Classroom ID
     */
    public Schedule(String startTime, String endTime, DayOfWeek dayOfWeek, Long courseId, Long classroomId) {
        this(new TimeRange(startTime, endTime), dayOfWeek, new CourseId(courseId), new ClassroomId(classroomId));
    }

    /**
     * Update schedule
     * @param timeRange Time range
     * @param dayOfWeek Day of week
     * @param courseId Course ID
     * @param classroomId Classroom ID
     */
    public void updateSchedule(TimeRange timeRange, DayOfWeek dayOfWeek, CourseId courseId, ClassroomId classroomId) {
        this.timeRange = timeRange;
        this.dayOfWeek = dayOfWeek;
        this.courseId = courseId;
        this.classroomId = classroomId;
        validateSchedule();
    }

    /**
     * Update schedule with string times
     * @param startTime Start time
     * @param endTime End time
     * @param dayOfWeek Day of week
     * @param courseId Course ID
     * @param classroomId Classroom ID
     */
    public void updateSchedule(String startTime, String endTime, DayOfWeek dayOfWeek, Long courseId, Long classroomId) {
        updateSchedule(new TimeRange(startTime, endTime), dayOfWeek, new CourseId(courseId), new ClassroomId(classroomId));
    }

    /**
     * Checks if this schedule conflicts with another schedule
     * @param other Other schedule
     * @return True if conflicts, false otherwise
     */
    public boolean conflictsWith(Schedule other) {
        if (other == null) return false;

        return dayOfWeek == other.dayOfWeek &&
                classroomId.equals(other.classroomId) &&
                timeRange.overlapsWith(other.timeRange);
    }


    // Setters
    public void setWeeklySchedule(WeeklySchedule weeklySchedule) { this.weeklySchedule = weeklySchedule; }

    private void validateSchedule() {
        if (timeRange == null) {
            throw new IllegalArgumentException("Time range must not be null");
        }
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Day of week must not be null");
        }
        if (courseId == null) {
            throw new IllegalArgumentException("Course ID must not be null");
        }
        if (classroomId == null) {
            throw new IllegalArgumentException("Classroom ID must not be null");
        }
    }
}