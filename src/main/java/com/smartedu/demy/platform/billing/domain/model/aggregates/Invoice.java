package com.smartedu.demy.platform.billing.domain.model.aggregates;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.InvoiceStatus;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Entity
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

    public Invoice(StudentId studentId, Money amount, LocalDate dueDate) {
        this.studentId = Objects.requireNonNull(studentId);
        this.amount = Objects.requireNonNull(amount);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.status = InvoiceStatus.PENDING;
    }

    public Invoice(CreateInvoiceCommand command) {
        this.studentId = new StudentId(command.studentId());
        this.amount = new Money(command.amount(), Currency.getInstance(command.currency()));
        this.dueDate = command.dueDate();
        this.status = InvoiceStatus.PENDING;
    }

    public void addPayment(Payment payment) {
        Objects.requireNonNull(payment, "Payment must not be null");
        payment.setInvoice(this);
        this.payments.add(payment);
        if (isPaid()) {
            this.status = InvoiceStatus.PAID;
        }
    }

    public Money getPaidAmount() {
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(Money.zero(this.amount.currency()), Money::add);
    }

    public boolean isPaid() {
        return getPaidAmount().isGreaterThanOrEqual(this.amount);
    }

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(this.payments);
    }
}
