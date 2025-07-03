package com.smartedu.demy.platform.billing.domain.model.aggregates;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateFinancialTransactionCommand;
import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.TransactionCategory;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.TransactionType;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class FinancialTransaction extends AuditableAbstractAggregateRoot<FinancialTransaction> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionCategory category;

    @Column(nullable = false)
    String concept;

    @Column(nullable = false)
    LocalDateTime date;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    Payment payment;

    protected FinancialTransaction() {}

    public FinancialTransaction(
            TransactionType type,
            TransactionCategory category,
            String concept,
            LocalDateTime date,
            Payment payment) {
        this.type = Objects.requireNonNull(type);
        this.category = Objects.requireNonNull(category);
        this.concept = Objects.requireNonNull(concept);
        this.date = Objects.requireNonNull(date);
        this.payment = Objects.requireNonNull(payment);
    }
}
