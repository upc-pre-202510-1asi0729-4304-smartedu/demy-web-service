package com.smartedu.demy.platform.scheduling.domain.model.aggregates;

import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateCourseCommand;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Entity
/**
 * Represents a course entity in the scheduling domain.
 * Extends AuditableAbstractAggregateRoot for auditing capabilities.
 */
public class Course extends AuditableAbstractAggregateRoot<Course> {

    /**
     * Name of the course.
     */
    private String name;
    /**
     * Unique code for the course.
     */
    private String code;
    /**
     * Description of the course.
     */
    private String description;

    /**
     * Default constructor. Initializes fields to empty strings.
     */
    public Course() {
        this.name = Strings.EMPTY;
        this.code = Strings.EMPTY;
        this.description = Strings.EMPTY;
    }

    /**
     * Constructor with parameters.
     * @param name Course name
     * @param code Course code
     * @param description Course description
     */
    public Course(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    /**
     * Constructor with command
     * @param command Create course command
     */
    public Course(CreateCourseCommand command) {
        this.name = command.name();
        this.code = command.code();
        this.description = command.description();
    }

    /**
     * Update course
     * @param name Course name
     * @param code Course code
     * @param description Course description
     */
    public Course updateCourse(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
        return this;
    }

    public Course updateCourse(UpdateCourseCommand command) {
        this.name = command.name();
        this.code = command.code();
        this.description = command.description();
        return this;
    }


}