package com.smartedu.demy.platform.scheduling.application.internal.queryservices;

import com.smartedu.demy.platform.scheduling.domain.model.aggregates.Classroom;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllClassroomsQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetClassroomByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.ClassroomQueryService;
import com.smartedu.demy.platform.scheduling.infrastructure.persistence.jpa.repositories.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomQueryServiceImpl implements ClassroomQueryService {

    private final ClassroomRepository classroomRepository;

    public ClassroomQueryServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<Classroom> handle(GetAllClassroomsQuery query) {
        return classroomRepository.findAll();
    }

    @Override
    public Optional<Classroom> handle(GetClassroomByIdQuery query) {
        return classroomRepository.findById(query.classroomId());
    }
}