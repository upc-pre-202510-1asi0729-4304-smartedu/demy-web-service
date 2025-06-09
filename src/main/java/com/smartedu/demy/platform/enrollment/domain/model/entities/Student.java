package com.smartedu.demy.platform.enrollment.domain.model.entities;

import com.smartedu.demy.platform.shared.domain.model.entities.AuditableModel;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Student extends AuditableModel {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    protected Student() {}

    public Student(String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.sex = sex;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public StudentId getStudentId() {
        return new StudentId(this.getId());
    }
}
