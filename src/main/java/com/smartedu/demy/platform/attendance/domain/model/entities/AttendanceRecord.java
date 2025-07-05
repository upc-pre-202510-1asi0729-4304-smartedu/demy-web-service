package com.smartedu.demy.platform.attendance.domain.model.entities;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceStatus;
import com.smartedu.demy.platform.shared.domain.model.entities.AuditableModel;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


/**
 * AttendanceRecord Entity.
 *
 * <p>
 * Represents the attendance of a single student in a class session,
 * linking the student's identifier (DNI) and their attendance status.
 * This entity is part of the Attendance bounded context.
 * </p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "attendance_record")
@Getter
public class AttendanceRecord extends AuditableModel {

    @Embedded
    @AttributeOverride(name = "dni", column = @Column(name = "student_dni", nullable = false))
    private Dni dni;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_session_id", nullable = false)
    private ClassSession classSession;

    /**
     * Transient field used for projection only (e.g., displaying student name without persistence).
     */
    @Transient
    @Setter
    private String studentName;

    /**
     * Required by JPA.
     */
    protected AttendanceRecord() {}


    /**
     * Creates a new AttendanceRecord with the given student DNI and attendance status.
     *
     * @param dni    the student's unique identifier (DNI), must not be null
     * @param status the attendance status, must not be null
     * @throws NullPointerException if dni or status is null
     */
    public AttendanceRecord(Dni dni, AttendanceStatus status) {
        this.dni = Objects.requireNonNull(dni, "StudentId must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }
}

