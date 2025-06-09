package com.smartedu.demy.platform.shared.domain.model.valueobjects;

import jakarta.persistence.*;

import java.util.Objects;

public record CourseId(
        @Column(name="course_id",nullable= false, updatable = false)
        Long value
) {
   public CourseId() { this(0L); }

    public CourseId {
       Objects.requireNonNull(value, "Course Id cannot be null");
       if(value <= 0)
           throw new IllegalArgumentException("Course Id cannot be less than 0");
    }
}
