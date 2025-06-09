package com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.billing.domain.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
