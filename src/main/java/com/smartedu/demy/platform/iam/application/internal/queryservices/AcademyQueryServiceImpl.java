package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import com.smartedu.demy.platform.iam.domain.services.AcademyQueryService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.AcademyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademyQueryServiceImpl implements AcademyQueryService {

    private final AcademyRepository academyRepository;

    public AcademyQueryServiceImpl(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    @Override
    public List<Academy> getAllAcademies() {
        return academyRepository.findAll();
    }
}
