package com.smartedu.demy.platform.acceptance.tests.steps;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PeriodId;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.StudentId;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.EnrollmentStatus;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.PaymentStatus;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;
import java.math.BigDecimal;
import java.util.Currency;
import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentSteps {
    private Long studentId;
    private Long periodId;
    private Long weeklyScheduleId;
    private Enrollment enrollment;
    private Exception exception;
    private String message;

    @Given("existe un Student con id {long}")
    public void existe_un_student_con_id(Long id) {
        this.studentId = id;
    }

    @Given("existe un AcademicPeriod con id {long}")
    public void existe_un_academic_period_con_id(Long id) {
        this.periodId = id;
    }


    @Given("existe un weeklyScheduleId con id {long}")
    public void existe_un_weekly_schedule_con_id(Long id) {
        this.weeklyScheduleId = id;
    }

    @When("intento registrar la matrícula con amount {bigDecimal} y currency {string}")
    public void intento_registrar_matricula(BigDecimal amount, String currencyCode) {
        try {
            enrollment = new Enrollment(
                    new StudentId(this.studentId),
                    new PeriodId(this.periodId),
                    new PeriodId(this.weeklyScheduleId),
                    new Money(amount, Currency.getInstance(currencyCode)),
                    EnrollmentStatus.ACTIVE,
                    PaymentStatus.PAID
            );
            message = "Registro exitoso";
        } catch (Exception e) {
            this.exception = e;
            message = e.getMessage();
        }
    }

    @Then("debe crearse una Enrollment con")
    public void debe_crearse_una_enrollment_con(io.cucumber.datatable.DataTable table) {
        if (exception != null) {
            fail("Se lanzó excepción: " + exception.getMessage());
        }
        var map = table.asMap(String.class, String.class);
        assertEquals(Long.valueOf(map.get("studentId")), enrollment.getStudentId().studentId());
        assertEquals(Long.valueOf(map.get("academicPeriodId")),  enrollment.getPeriodId().periodId());
        assertEquals(Long.valueOf(map.get("weeklyScheduleId")),  enrollment.getWeeklyScheduleId().weeklyScheduleId());
        assertEquals(map.get("amount"),     enrollment.getAmount().amount());
        assertEquals(map.get("currency"),   enrollment.getAmount().currency().getCurrencyCode());
        assertEquals(map.get("enrollmentStatus"), enrollment.getEnrollmentStatus().name());
        assertEquals(map.get("paymentStatus"),    enrollment.getPaymentStatus().name());
    }

    @Then("el mensaje final es {string}")
    public void el_mensaje_final_es(String expectedMessage) {
        assertEquals(expectedMessage, message);
    }

    @ParameterType("[-+]?[0-9]*\\.?[0-9]+")
    public BigDecimal bigDecimal(String value) {
        return new BigDecimal(value);
    }
}

