package com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.WeeklySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklySchedule, Long> {

    Optional<WeeklySchedule> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}