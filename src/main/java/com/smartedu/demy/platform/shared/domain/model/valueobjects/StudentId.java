package com.smartedu.demy.platform.shared.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record StudentId(
        @Column(name = "student_id", nullable = false, updatable = false)
        Long value
) {
    public StudentId() { this(0L); }

    public StudentId {
        Objects.requireNonNull(value, "Student ID must not be null");
    }


}
