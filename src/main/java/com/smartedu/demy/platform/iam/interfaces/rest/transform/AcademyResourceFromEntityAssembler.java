package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.AcademyResource;

/**
 * Assembler class to transform {@link Academy} domain entities
 * into {@link AcademyResource} DTOs for API responses.
 */
public class AcademyResourceFromEntityAssembler {

    /**
     * Converts an {@link Academy} entity into an {@link AcademyResource}.
     *
     * @param academy the domain entity to convert
     * @return a new {@link AcademyResource} representing the academy
     */
    public static AcademyResource toResourceFromEntity(Academy academy) {
        return new AcademyResource(
                academy.getId().toString(),
                academy.getAcademyName(),
                academy.getRuc()
        );
    }
}
