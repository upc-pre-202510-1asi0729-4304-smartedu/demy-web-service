package com.smartedu.demy.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(
        @Column(name = "first_name", nullable = false)
        String firstName,

        @Column(name = "last_name", nullable = false)
        String lastName
) {

    public FullName {
        if (firstName == null ) {
            throw new IllegalArgumentException("First name must not be null");
        }
        else if (lastName == null ) {
            throw new IllegalArgumentException("Last Name must not be null");
        }
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}