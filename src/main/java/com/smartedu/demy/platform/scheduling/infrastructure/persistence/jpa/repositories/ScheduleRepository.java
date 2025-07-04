package com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.scheduling.domain.model.entities.Schedule;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTeacherId(UserId teacherId);
}
