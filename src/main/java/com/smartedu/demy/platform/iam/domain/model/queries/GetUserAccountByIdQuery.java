package com.smartedu.demy.platform.iam.domain.model.queries;

public record GetUserAccountByIdQuery(Long id) {
    public GetUserAccountByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User account id must not be null or negative");
        }
    }
}
