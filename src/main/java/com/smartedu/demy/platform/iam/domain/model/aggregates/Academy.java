package com.smartedu.demy.platform.iam.domain.model.aggregates;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.AcademicPeriod;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Aggregate root representing an Academy in the system.
 * An Academy is linked to a user (typically an admin) and contains identifying information
 * such as its name and RUC. It may also be associated with academic periods.
 */
@Entity
@NoArgsConstructor
public class Academy extends AuditableAbstractAggregateRoot<Academy> {

    /**
     * The user ID that owns or manages the academy.
     */
    @Embedded
    @Getter
    private UserId userId;

    /**
     * The name of the academy.
     */
    @Getter
    private String academyName;


    /**
     * The RUC (Registro Único de Contribuyentes) of the academy. Cannot be null.
     */
    @Column(nullable = false)
    @Getter
    private String ruc;

    /**
     * The list of academic periods associated with this academy.
     * This field is transient and not persisted in the database.
     */
    @Transient
    @Getter
    private List<AcademicPeriod> periods = List.of();

    /**
     * Constructs a new {@code Academy} with the specified user ID, academy name, and RUC.
     *
     * @param userId      the ID of the user who owns the academy.
     * @param academyName the name of the academy.
     * @param ruc         the tax ID number of the academy.
     */
    public Academy(UserId userId, String academyName, String ruc) {
        this.userId = userId;
        this.academyName = academyName;
        this.ruc = ruc;
    }

    /**
     * Updates the name and RUC of the academy.
     *
     * @param academyName the new name of the academy.
     * @param ruc         the new RUC of the academy.
     */
    public void updateName(String academyName, String ruc) {
        this.academyName = academyName;
        this.ruc = ruc;
    }
}
