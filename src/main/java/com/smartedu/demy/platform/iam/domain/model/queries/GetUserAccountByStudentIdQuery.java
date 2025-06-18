package com.smartedu.demy.platform.iam.domain.model.queries;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;

public record GetUserAccountByStudentIdQuery(UserId studentId) {
}
