package com.smartedu.demy.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


/**
 * Value object representing a user's full name, composed of a first name and a last name.
 * Ensures both components are non-null.
 *
 * This class is embeddable and intended to be persisted as part of an entity.
 *
 * @param firstName the user's first name. Must not be null.
 * @param lastName  the user's last name. Must not be null.
 * @throws IllegalArgumentException if either the first name or last name is null.
 */
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

    /**
     * Returns the full name in the format: "FirstName LastName".
     *
     * @return the concatenated full name.
     */
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}