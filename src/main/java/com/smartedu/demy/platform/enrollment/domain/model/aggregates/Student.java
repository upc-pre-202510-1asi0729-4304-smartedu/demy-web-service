package com.smartedu.demy.platform.enrollment.domain.model.aggregates;

import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateStudentCommand;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PersonName;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PhoneNumber;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.Sex;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "students")
public class Student extends AuditableAbstractAggregateRoot<Student> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = false))
    })
    private PersonName fullName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "dni", column = @Column(name = "dni", nullable = false, unique = true))
    })
    private Dni dni;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number", nullable = false))
    })
    private PhoneNumber phoneNumber;

    /**
     * Default constructor for JPA
     */
    public Student() {}

    /**
     * Constructor for creating a new Student
     *
     * @param firstName  The first name of the student
     * @param lastName   The last name of the student
     * @param dni        The student's national ID
     * @param sex        The student's biological sex
     * @param birthDate  The student's birthdate
     * @param address    The student's address
     * @param phoneNumber The student's phone number
     */
    public Student(String firstName, String lastName, String dni, Sex sex, LocalDate birthDate, String address, String phoneNumber) {
        this();
        this.fullName = new PersonName(firstName, lastName);
        this.dni = new Dni(dni);
        this.sex = sex;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    /**
     * Constructor for creating a new Student from command data
     */
    public Student(CreateStudentCommand command) {
        this.fullName = new PersonName(command.firstName(), command.lastName());
        this.dni = new Dni(command.dni());
        this.sex = Sex.valueOf(command.sex().toUpperCase());
        this.birthDate = command.birthDate();
        this.address = command.address();
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
    }

    /**
     * Update student information
     */
    public Student updateInformation(String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber) {
        this.fullName = new PersonName(firstName, lastName);
        this.dni = new Dni(dni);
        this.sex = Sex.valueOf(sex.toUpperCase());
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = new PhoneNumber(phoneNumber);
        return this;
    }
}
