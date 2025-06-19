package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.time.LocalDate;

public record AcademicPeriodResource (Long academicPeriodId, String periodName, LocalDate startDate, LocalDate endDate, Boolean isActive) {
}
