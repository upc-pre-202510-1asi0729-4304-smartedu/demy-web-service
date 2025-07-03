package com.smartedu.demy.platform.iam.domain.model.aggregates;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.AcademicPeriod;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Academy extends AuditableAbstractAggregateRoot<Academy> {

    @Embedded
    @Getter
    private UserId userId;

    @Getter
    private String academyName;

    @Column(nullable = false)
    @Getter
    private String ruc;

    @Transient
    @Getter
    private List<AcademicPeriod> periods = List.of();

    public Academy(UserId userId, String academyName, String ruc) {
        this.userId = userId;
        this.academyName = academyName;
        this.ruc = ruc;
    }

    public void updateName(String academyName, String ruc) {
        this.academyName = academyName;
        this.ruc = ruc;
    }
}
