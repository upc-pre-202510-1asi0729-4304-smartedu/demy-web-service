package com.smartedu.demy.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public record UserId(UUID value) {}