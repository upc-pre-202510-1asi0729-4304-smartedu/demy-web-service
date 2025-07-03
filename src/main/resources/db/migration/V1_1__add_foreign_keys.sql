ALTER TABLE schedules
    ADD CONSTRAINT FK_classrooms_id
        FOREIGN KEY (classroom_id)
            REFERENCES classrooms (id);
ALTER TABLE schedules
    ADD CONSTRAINT FK_courses_id
        FOREIGN KEY (course_id)
            REFERENCES courses (id);