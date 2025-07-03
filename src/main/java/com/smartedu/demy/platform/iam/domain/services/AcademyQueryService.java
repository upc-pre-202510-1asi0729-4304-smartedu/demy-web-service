package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;

import java.util.List;

public interface AcademyQueryService {
    List<Academy> getAllAcademies();
}
