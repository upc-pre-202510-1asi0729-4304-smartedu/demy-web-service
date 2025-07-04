package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;

import java.util.List;

/**
 * Service interface for querying academy-related information.
 * Provides read-only operations related to {@link Academy} entities.
 */
public interface AcademyQueryService {

    /**
     * Retrieves a list of all academies in the system.
     *
     * @return a list of {@link Academy} objects.
     */
    List<Academy> getAllAcademies();
}
