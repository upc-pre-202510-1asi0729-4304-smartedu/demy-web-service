package com.smartedu.demy.platform.attendance.domain.model.valueobjects;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;
import jakarta.persistence.*;

/**
 *AttendanceRecord Value Object
 */
@Embeddable
public record AttendanceRecord(
        @Embedded
        @Column(nullable = false)
        StudentId studentId,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        AttendanceStatus status
) {
    /**
     * Default constructor
     */
    protected AttendanceRecord(){
       this(null,null);
    }
}
