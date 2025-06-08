package com.smartedu.demy.platform.billing.domain.model.aggregates;

import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    @Embedded
    @Getter
    private final StudentId studentId;

    @Embedded
    @Getter
    private Money amount;

    @Column(nullable = false)
    @Getter
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Payment> payments = new ArrayList<>();

    protected Invoice() {
        this.studentId = null;
        this.amount = null;
        this.dueDate = null;
        this.status = null;
    }

    public Invoice(CreateInvoiceCommand command) {
        this.studentId = command.studentId();
        this.amount = command.amount();
        this.dueDate = command.dueDate();
        this.status = command.status();
    }

    // public void addPayment(Payment payment) {}

    // public boolean isPaid() {}

    // public Money getPaidAmount() {}

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(this.payments);
    }
}
