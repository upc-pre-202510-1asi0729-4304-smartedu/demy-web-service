package com.smartedu.demy.platform.iam.domain.model.queries;

/**
 * Query object used to request a user account by its unique identifier.
 * Ensures the ID is not null or negative before proceeding.
 *
 * @param id the unique identifier of the user account to retrieve.
 * @throws IllegalArgumentException if the id is null or less than or equal to zero.
 */
public record GetUserAccountByIdQuery(Long id) {
    public GetUserAccountByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User account id must not be null or negative");
        }
    }
}
