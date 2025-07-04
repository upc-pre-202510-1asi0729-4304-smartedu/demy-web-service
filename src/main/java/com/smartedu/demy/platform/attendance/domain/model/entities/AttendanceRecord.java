package com.smartedu.demy.platform.attendance.domain.model.entities;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceStatus;
import com.smartedu.demy.platform.shared.domain.model.entities.AuditableModel;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
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
    @AttributeOverride(name = "dni", column = @Column(name = "student_dni", nullable = false))
    private Dni dni;


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
    public AttendanceRecord(Dni dni, AttendanceStatus status) {
        this.dni = Objects.requireNonNull(dni, "StudentId must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }
}

