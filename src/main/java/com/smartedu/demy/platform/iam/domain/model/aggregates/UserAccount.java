package com.smartedu.demy.platform.iam.domain.model.aggregates;

import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Email;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;

import jakarta.persistence.*;
import lombok.Getter;


/**
 * Aggregate root representing a user account within the IAM (Identity and Access Management) context.
 * A user account includes identity information such as full name and email, security credentials,
 * a role defining access level, and the account's status.
 */
@Entity
public class UserAccount extends AuditableAbstractAggregateRoot<UserAccount> {

    /**
     * The full name of the user.
     */
    @Embedded
    @Getter
    private FullName fullName;

    /**
     * The email address used for identification and login.
     */
    @Embedded
    @Getter
    private Email email;

    /**
     * The hashed password used for authentication.
     */
    @Column(nullable = false)
    @Getter
    private String passwordHash;

    /**
     * The role assigned to the user (e.g., ADMIN, TEACHER).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private Roles role;


    /**
     * The current status of the account (e.g., ACTIVE, INACTIVE).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private AccountStatus status;

    /**
     * Protected no-args constructor for JPA.
     */
    protected UserAccount() {
        this.fullName = null;
        this.email = null;
        this.passwordHash = null;
        this.role = null;
        this.status = null;
    }

    /**
     * Constructs a new user account with the specified attributes.
     *
     * @param fullName     the full name of the user.
     * @param email        the user's email address.
     * @param passwordHash the hashed password.
     * @param role         the role assigned to the user.
     * @param status       the account's current status.
     */
    public UserAccount(FullName fullName, Email email, String passwordHash, Roles role, AccountStatus status) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
    }

    /**
     * Updates the user's full name and email.
     *
     * @param fullName the new full name.
     * @param email    the new email address.
     */
    public void updateProfile(FullName fullName, Email email) {
        this.fullName = fullName;
        this.email = email;
    }

    /**
     * Updates the user's role.
     *
     * @param role the new role to assign.
     */
    public void updateRole(Roles role) {
        this.role = role;
    }

    /**
     * Updates the user's account status.
     *
     * @param status the new account status.
     */
    public void updateStatus(AccountStatus status) {
        this.status = status;
    }

    /**
     * Updates the user's hashed password.
     *
     * @param newHashedPassword the new hashed password.
     */
    public void updatePassword(String newHashedPassword) {
        this.passwordHash = newHashedPassword;
    }

    /**
     * Returns the domain-specific user ID value object based on the JPA ID.
     *
     * @return the {@link UserId} representing the user's unique identifier.
     */
    public UserId getUserId() {
        return new UserId(this.getId());
    }
}