package com.smartedu.demy.platform.billing.domain.model.queries;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;

public record GetInvoiceByStudentIdQuery(StudentId studentId) {
}
