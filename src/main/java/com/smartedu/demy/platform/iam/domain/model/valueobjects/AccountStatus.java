package com.smartedu.demy.platform.iam.domain.model.valueobjects;

/**
 * Enumeration representing the possible statuses of a user account.
 */
public enum AccountStatus {

    /**
     * The account is active and fully functional.
     */
    ACTIVE,

    /**
     * The account is inactive and may require activation or verification.
     */
    INACTIVE,

    /**
     * The account is blocked due to a violation, security concern, or administrative action.
     */
    BLOCKED
}