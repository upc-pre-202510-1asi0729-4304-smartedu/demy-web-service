package com.smartedu.demy.platform.enrollment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Dni(String dni) {

    public Dni() {
        this(null);
    }

    public Dni {
        if (dni == null || dni.length() != 8) {
            throw new IllegalArgumentException("DNI must be exactly 8 characters.");
        }
    }

}
