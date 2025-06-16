package com.smartedu.demy.platform.iam.domain.model.aggregates;

import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.model.resources.Email;
import com.smartedu.demy.platform.iam.domain.model.resources.FullName;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;



@Entity
public class UserAccount extends AuditableAbstractAggregateRoot<UserAccount> {

    @Embedded
    @Getter
    private final UserId userId;

    @Embedded
    @Getter
    private FullName fullName;

    @Embedded
    @Getter
    private Email email;

    @Column(nullable = false)
    @Getter
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private Roles role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private AccountStatus status;




    protected UserAccount() {
        this.userId = null;
        this.fullName = null;
        this.email = null;
        this.passwordHash = null;
        this.role = null;
        this.status = null;
    }

    public UserAccount(UserId userId, FullName fullName, Email email, String passwordHash, Roles role, AccountStatus status) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
    }
    public void updateProfile(FullName fullName, Email email) {
        this.fullName = fullName;
        this.email = email;
    }
    public void updateRole(Roles role) {
        this.role = role;
    }

    public void updateStatus(AccountStatus status) {
        this.status = status;
    }
    public void updatePassword(String newHashedPassword) {
        this.passwordHash = newHashedPassword;
    }

}