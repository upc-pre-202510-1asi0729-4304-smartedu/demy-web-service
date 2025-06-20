package com.smartedu.demy.platform.enrollment.domain.model.aggregates;

import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.*;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Entity
@Table(name = "enrollments")
public class Enrollment extends AuditableAbstractAggregateRoot<Enrollment> {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "student_id", nullable = false))
    private StudentId studentId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "period_id", nullable = false))
    private PeriodId periodId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false))
    })
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_status", nullable = false)
    private EnrollmentStatus enrollmentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    /**
     * Default constructor for JPA
     */
    public Enrollment() {}

    /**
     * Constructor for creating a new Enrollment
     *
     * @param studentId The ID of the student
     * @param periodId The ID of the academic period
     * @param amount The payment amount
     * @param enrollmentStatus The enrollment status
     * @param paymentStatus The payment status
     */
    public Enrollment(StudentId studentId, PeriodId periodId, Money amount, EnrollmentStatus enrollmentStatus, PaymentStatus paymentStatus) {
        this.studentId = studentId;
        this.periodId = periodId;
        this.amount = amount;
        this.enrollmentStatus = enrollmentStatus;
        this.paymentStatus = paymentStatus;
    }

    /**
     * Constructor for creating a new Enrollment from command data
     */
    public Enrollment(CreateEnrollmentCommand command) {
        this.studentId = new StudentId(command.studentId());
        this.periodId = new PeriodId(command.periodId());
        this.amount = new Money(command.amount(), command.currency());
        this.enrollmentStatus = EnrollmentStatus.valueOf(command.enrollmentStatus().toUpperCase());
        this.paymentStatus = PaymentStatus.valueOf(command.paymentStatus().toUpperCase());
    }

    /**
     * Update enrollment information
     */
    public Enrollment updateInformation(BigDecimal amount, Currency currency, String enrollmentStatus, String paymentStatus) {
        this.amount = new Money(amount, currency);
        this.enrollmentStatus = EnrollmentStatus.valueOf(enrollmentStatus.toUpperCase());
        this.paymentStatus = PaymentStatus.valueOf(paymentStatus.toUpperCase());
        return this;
    }
}