package com.smartedu.demy.platform.shared.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record UserId(
        @Column(name = "user_id", nullable = false, updatable = false)
        Long value
) {
    public UserId() { this(0L); }

    public UserId {
        Objects.requireNonNull(value, "User ID must not be null");
    }
}
