package com.smartedu.demy.platform.billing.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.billing.domain.model.aggregates.Invoice;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByStudentId(StudentId studentId);
}
