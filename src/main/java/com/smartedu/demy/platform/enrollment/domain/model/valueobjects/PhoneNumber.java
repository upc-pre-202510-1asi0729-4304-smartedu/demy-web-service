package com.smartedu.demy.platform.enrollment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String phoneNumber) {
    public PhoneNumber() {
        this(null);
    }
    public PhoneNumber {
        if (phoneNumber == null || phoneNumber.length() != 9) {
            throw new IllegalArgumentException("Phone number must be exactly 9 digits.");
        }
    }
}
