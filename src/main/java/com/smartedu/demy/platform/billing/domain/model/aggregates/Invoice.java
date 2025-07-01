package com.smartedu.demy.platform.billing.domain.model.aggregates;

import com.smartedu.demy.platform.billing.domain.model.commands.CreateInvoiceCommand;
import com.smartedu.demy.platform.billing.domain.model.valueobjects.InvoiceStatus;
import com.smartedu.demy.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Money;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    @Embedded
    @Getter
    private Dni dni;

    @Getter
    private String name;

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

    protected Invoice() {
        this.dni = null;
        this.amount = null;
        this.dueDate = null;
        this.status = null;
    }

    public Invoice(Dni dni, String name, Money amount, LocalDate dueDate) {
        this.dni = Objects.requireNonNull(dni);
        this.name = name;
        this.amount = Objects.requireNonNull(amount);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.status = InvoiceStatus.PENDING;
    }

    public Invoice(CreateInvoiceCommand command) {
        this.dni = new Dni(command.dni());
        this.amount = new Money(command.amount(), Currency.getInstance(command.currency()));
        this.dueDate = command.dueDate();
        this.status = InvoiceStatus.PENDING;
    }

    public boolean isPaid() {
        return this.status == InvoiceStatus.PAID;
    }
}
