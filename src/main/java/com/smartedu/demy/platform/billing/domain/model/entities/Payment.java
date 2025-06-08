package com.smartedu.demy.platform.billing.domain.model.entities;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.PaymentMethod;
import com.smartedu.demy.platform.shared.domain.model.entities.AuditableModel;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Payment extends AuditableModel {

    @Getter
    @Embedded
    private Money amount;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Getter
    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Invoice invoice;

    protected Payment() {}

    public Payment(Money amount, PaymentMethod method, LocalDateTime paidAt) {
        this.amount = Objects.requireNonNull(amount, "Amount must not be null");
        this.method = Objects.requireNonNull(method, "Method must not be null");
        this.paidAt = Objects.requireNonNull(paidAt, "PaidAt must not be null");
    }
}
