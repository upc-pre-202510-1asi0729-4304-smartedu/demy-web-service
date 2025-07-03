package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.AcademyResource;

public class AcademyResourceFromEntityAssembler {
    public static AcademyResource toResourceFromEntity(Academy academy) {
        return new AcademyResource(
                academy.getId().toString(),
                academy.getAcademyName(),
                academy.getRuc()
        );
    }
}
