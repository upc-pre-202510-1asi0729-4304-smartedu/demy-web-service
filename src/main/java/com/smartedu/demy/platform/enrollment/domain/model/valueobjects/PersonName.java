package com.smartedu.demy.platform.enrollment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String firstName, String lastName) {
    /**
     * Default constructor
     */
    public PersonName() {
        this(null, null);
    }

    /**
     * Full name getter
     * @return Full name
     */
    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }

    /**
     * Constructor with validation
     * @param firstName First name
     * @param lastName Last name
     */
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be null or blank");
        }
    }
}
