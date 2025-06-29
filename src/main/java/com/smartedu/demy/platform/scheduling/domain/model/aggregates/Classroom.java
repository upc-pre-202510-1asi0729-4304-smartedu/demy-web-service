package com.smartedu.demy.platform.scheduling.domain.model.aggregates;

import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.model.commands.UpdateClassroomCommand;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Entity
public class Classroom extends AuditableAbstractAggregateRoot<Classroom> {

    private String code;
    private Integer capacity;
    private String campus;

    /**
     * Default constructor
     */
    public Classroom() {
        this.code = Strings.EMPTY;
        this.capacity = Integer.valueOf(0);
        this.campus = Strings.EMPTY;
    }

    /**
     * Constructor with parameters
     * @param code Classroom code
     * @param capacity Classroom capacity
     * @param campus Campus name
     */
    public Classroom(String code, Integer capacity, String campus) {
        this.code = code;
        this.capacity = capacity;
        this.campus = campus;
    }

    /**
     * Constructor with command
     * @param command Create classroom command
     */
    public Classroom(CreateClassroomCommand command) {
        this.code = command.code();
        this.capacity = command.capacity();
        this.campus = command.campus();
    }

    /**
     * Update classroom
     * @param code Classroom code
     * @param capacity Classroom capacity
     * @param campus Campus name
     */
    public Classroom updateClassroom(String code, Integer capacity, String campus) {
        this.code = code;
        this.capacity = capacity;
        this.campus = campus;
        return this;
    }

    public Classroom updateClassroom(UpdateClassroomCommand command) {
        this.code = command.code();
        this.capacity = command.capacity();
        this.campus = command.campus();
        return this;
    }

}