package com.smartedu.demy.platform.shared.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@EqualsAndHashCode
public class Money {

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Currency currency;

    protected Money() {}

    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and currency must not be null");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    public Money add(Money other) {
        checkCurrencyMatch(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        checkCurrencyMatch(other);
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.signum() < 0) {
            throw new IllegalArgumentException("Money result cannot be negative");
        }
        return new Money(result, this.currency);
    }

    public boolean isGreaterThanOrEqual(Money other) {
        checkCurrencyMatch(other);
        return this.amount.compareTo(other.amount) >= 0;
    }

    public boolean isZero() {
        return this.amount.signum() == 0;
    }

    private void checkCurrencyMatch(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currency + " vs " + other.currency);
        }
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }
}
