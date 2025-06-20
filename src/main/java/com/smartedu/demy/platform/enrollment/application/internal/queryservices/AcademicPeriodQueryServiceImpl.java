package com.smartedu.demy.platform.enrollment.application.internal.queryservices;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.AcademicPeriod;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAcademicPeriodByIdQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllAcademicPeriodsQuery;
import com.smartedu.demy.platform.enrollment.domain.services.AcademicPeriodQueryService;
import com.smartedu.demy.platform.enrollment.infrastructure.persistence.jpa.repositories.AcademicPeriodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicPeriodQueryServiceImpl implements AcademicPeriodQueryService {
    private final AcademicPeriodRepository academicPeriodRepository;

    public AcademicPeriodQueryServiceImpl(AcademicPeriodRepository academicPeriodRepository) {
        this.academicPeriodRepository = academicPeriodRepository;
    }

    @Override
    public List<AcademicPeriod> handle(GetAllAcademicPeriodsQuery query) {
        return academicPeriodRepository.findAll();
    }

    @Override
    public Optional<AcademicPeriod> handle(GetAcademicPeriodByIdQuery query) {
        return academicPeriodRepository.findById(query.academicPeriodId());
    }
}
