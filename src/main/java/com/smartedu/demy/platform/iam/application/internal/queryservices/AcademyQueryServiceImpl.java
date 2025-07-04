package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import com.smartedu.demy.platform.iam.domain.services.AcademyQueryService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.AcademyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link AcademyQueryService} interface.
 * Provides read-only operations related to Academy entities.
 */
@Service
public class AcademyQueryServiceImpl implements AcademyQueryService {

    private final AcademyRepository academyRepository;

    /**
     * Constructs the query service with the required repository.
     *
     * @param academyRepository the repository used to access academy data.
     */
    public AcademyQueryServiceImpl(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    /**
     * Retrieves all academies from the data source.
     *
     * @return a list of all {@link Academy} entities.
     */
    @Override
    public List<Academy> getAllAcademies() {
        return academyRepository.findAll();
    }
}
