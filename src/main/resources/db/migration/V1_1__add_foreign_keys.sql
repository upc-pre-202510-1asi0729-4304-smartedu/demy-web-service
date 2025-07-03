ALTER TABLE enrollments
    ADD CONSTRAINT FK_enrollments_students_id
        FOREIGN KEY (student_id)
            REFERENCES students (id);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_enrollments_academic_periods_id
        FOREIGN KEY (period_id)
            REFERENCES academic_periods (id);
