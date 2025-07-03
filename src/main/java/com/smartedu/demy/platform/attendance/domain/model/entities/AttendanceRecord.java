package com.smartedu.demy.platform.attendance.domain.model.entities;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceStatus;
import com.smartedu.demy.platform.shared.domain.model.entities.AuditableModel;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


/**
 *AttendanceRecord Entity
 */
@Entity
@Table(name = "attendance_record")
@Getter
public class AttendanceRecord extends AuditableModel {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "student_id", nullable = false))
    })
    private StudentId studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_session_id", nullable = false)
    private ClassSession classSession;

    /** Constructor requerido por JPA */
    protected AttendanceRecord() {}

    /** Constructor de negocio */
    public AttendanceRecord(StudentId studentId, AttendanceStatus status) {
        this.studentId = Objects.requireNonNull(studentId, "StudentId must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }
}

