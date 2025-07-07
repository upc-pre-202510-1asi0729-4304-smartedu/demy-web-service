package com.smartedu.demy.platform.unit.tests;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PeriodId;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.StudentId;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.WeeklyScheduleId;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.EnrollmentStatus;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTests {

    @Test
    public void constructor_WithValidData_ShouldSetAllFields() {
        // Arrange
        StudentId sid = new StudentId(10L);
        PeriodId pid = new PeriodId(20L);
        WeeklyScheduleId wsid = new WeeklyScheduleId(30L);
        Money amount = new Money(new BigDecimal("1500.00"), Currency.getInstance("PEN"));
        EnrollmentStatus es = EnrollmentStatus.ACTIVE;
        PaymentStatus ps = PaymentStatus.PAID;

        // Act
        Enrollment enrollment = new Enrollment(sid, pid, wsid, amount, es, ps);

        // Assert
        assertEquals(sid, enrollment.getStudentId());
        assertEquals(pid, enrollment.getAcademicPeriodId());
        assertEquals(wsid, enrollment.getWeeklyScheduleId());
        assertEquals(amount.amount(), enrollment.getAmount().amount());
        assertEquals(amount.currency(), enrollment.getAmount().currency());
        assertEquals(es, enrollment.getEnrollmentStatus());
        assertEquals(ps, enrollment.getPaymentStatus());
    }


    @Test
    public void updateInformation_WithValidData_ShouldChangeFields() {
        // Arrange
        Enrollment enrollment = new Enrollment(
                new StudentId(5L),
                new PeriodId(5L),
                new WeeklyScheduleId(5L),
                new Money(new BigDecimal("1000.00"), Currency.getInstance("PEN")),
                EnrollmentStatus.ACTIVE,
                PaymentStatus.PAID
        );
        BigDecimal newAmount = new BigDecimal("2000.00");
        Currency newCurrency = Currency.getInstance("USD");
        String newEnrStatus = "CANCELLED";
        String newPayStatus = "PENDING";

        // Act
        enrollment.updateInformation(newAmount, newCurrency, newEnrStatus, newPayStatus);

        // Assert
        assertEquals(newAmount, enrollment.getAmount().amount());
        assertEquals(newCurrency, enrollment.getAmount().currency());
        assertEquals(EnrollmentStatus.CANCELLED, enrollment.getEnrollmentStatus());
        assertEquals(PaymentStatus.PENDING, enrollment.getPaymentStatus());
    }
}
