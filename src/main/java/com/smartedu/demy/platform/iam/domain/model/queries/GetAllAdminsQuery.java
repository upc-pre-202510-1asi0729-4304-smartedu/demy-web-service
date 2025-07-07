package com.smartedu.demy.platform.iam.domain.model.queries;

/**
 * Query object used to request the retrieval of all user accounts with the ADMIN role.
 * Typically handled by a query service that filters users by their assigned role.
 */
public record GetAllAdminsQuery() {
}
