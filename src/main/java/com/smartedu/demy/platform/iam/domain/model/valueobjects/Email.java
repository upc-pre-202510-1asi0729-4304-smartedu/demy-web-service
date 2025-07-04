package com.smartedu.demy.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Value object representing an email address associated with a user account.
 * Ensures that the email value is not null.
 *
 * This class is embeddable and intended to be persisted as part of an entity.
 *
 * @param value the email address string. Must not be null.
 * @throws NullPointerException if the value is null.
 */
@Embeddable
public record Email(
    @Column(name = "email", nullable = false, unique = true)
    String value){
    public Email{
        if(value == null) {
            throw new NullPointerException("Email value must not be null");
        }
    }
}