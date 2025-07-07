ALTER TABLE schedules
    ADD CONSTRAINT FK_classrooms_id
        FOREIGN KEY (classroom_id)
            REFERENCES classrooms (id);
ALTER TABLE schedules
    ADD CONSTRAINT FK_courses_id
        FOREIGN KEY (course_id)
            REFERENCES courses (id);
ALTER TABLE schedules
    ADD CONSTRAINT FK_user_accounts_id
        FOREIGN KEY (teacher_id)
            REFERENCES user_accounts (id);
ALTER TABLE enrollments
    ADD CONSTRAINT FK_enrollments_students_id
        FOREIGN KEY (student_id)
            REFERENCES students (id);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_enrollments_academic_periods_id
        FOREIGN KEY (period_id)
            REFERENCES academic_periods (id);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_enrollments_weekly_schedule_id
        FOREIGN KEY (weekly_schedule_id)
            REFERENCES weekly_schedules (id);
