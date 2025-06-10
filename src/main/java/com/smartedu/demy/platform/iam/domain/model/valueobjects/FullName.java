package com.smartedu.demy.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(String value) {}