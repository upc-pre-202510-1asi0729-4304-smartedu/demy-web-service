package com.smartedu.demy.platform.attendance.infrastructure.persistence.jpa;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceRecord;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * JPA repository for ClassSession entity
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for ClassSession entity
 * It extends Spring Data JpaRepository with ClassSession as the entity type and Long as the ID type
 * @since 1.0
 */
public interface ClassSessionRepository
   extends JpaRepository<ClassSession, Long> {

    boolean existsByCourseIdAndDate (CourseId courseId, LocalDate date);
}
