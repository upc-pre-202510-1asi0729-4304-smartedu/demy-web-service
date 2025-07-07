package com.smartedu.demy.platform.acceptance.tests.steps;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.valueobjects.*;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

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


    @Given("existe un WeeklySchedule con id {long}")
    public void existe_un_weekly_schedule_con_id(Long id) {
        this.weeklyScheduleId = id;
    }

    @When("intento registrar la matrícula con amount {bigDecimal} y currency {word}")
    public void intento_registrar_la_matricula(BigDecimal amount, String currencyCode) {
        try {
            enrollment = new Enrollment(
                    new StudentId(this.studentId),
                    new PeriodId(this.periodId),
                    new WeeklyScheduleId(this.weeklyScheduleId),
                    new Money(amount, Currency.getInstance(currencyCode)),
                    EnrollmentStatus.ACTIVE,
                    PaymentStatus.PAID
            );
            message = "Test Passed";
        } catch (Exception e) {
            this.exception = e;
            // si el dominio lanza un mensaje que empieza por "Error:", lo dejamos entero
            message = e.getMessage().startsWith("Error:") ? e.getMessage() : "Error";
        }
    }
    @Then("debe crearse una Enrollment con")
    public void debe_crearse_una_enrollment_con(DataTable table) {
        if (exception != null) {
            return;
        }

        Map<String,String> map = new HashMap<>(table.asMap(String.class, String.class));

        map.replaceAll((k,v) -> v.replaceAll("^\"|\"$", ""));

        assertEquals(Long.valueOf(map.get("studentId")), enrollment.getStudentId().studentId());
        assertEquals(Long.valueOf(map.get("academicPeriodId")), enrollment.getAcademicPeriodId().periodId());
        assertEquals(Long.valueOf(map.get("weeklyScheduleId")), enrollment.getWeeklyScheduleId().weeklyScheduleId());
        assertEquals(new BigDecimal(map.get("amount")), enrollment.getAmount().amount());
        assertEquals(map.get("currency"), enrollment.getAmount().currency().getCurrencyCode());
        assertEquals(map.get("enrollmentStatus"), enrollment.getEnrollmentStatus().name());
        assertEquals(map.get("paymentStatus"), enrollment.getPaymentStatus().name());
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

