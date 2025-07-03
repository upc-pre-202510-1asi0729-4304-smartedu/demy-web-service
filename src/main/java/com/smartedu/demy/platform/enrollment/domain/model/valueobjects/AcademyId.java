package com.smartedu.demy.platform.enrollment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AcademyId(Long id) {

    public AcademyId() {
        this(null);
    }

    public AcademyId {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Academy ID must not be null or less than 1");
        }
    }
}