package com.smartedu.demy.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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