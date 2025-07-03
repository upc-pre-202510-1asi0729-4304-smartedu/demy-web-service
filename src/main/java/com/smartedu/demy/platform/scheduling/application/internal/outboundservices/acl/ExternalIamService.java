package com.smartedu.demy.platform.scheduling.application.internal.outboundservices.acl;

import com.smartedu.demy.platform.iam.interfaces.acl.IamContextFacade;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Obtiene el ID del profesor a partir de su nombre completo usando el ACL de IAM.
     * @param firstName Nombre
     * @param lastName Apellido
     * @return ID del profesor, o 0L si no existe o no es profesor
     */

    public Optional<UserId> fetchTeacherIdByFullName(String firstName, String lastName) {
        var teacherId = iamContextFacade.fetchTeacherIdByFullName(firstName, lastName);
        if (teacherId == null || teacherId == 0L) return Optional.empty();
        return Optional.of(new UserId(teacherId));
    }

//    public Long fetchTeacherIdByFullName(String firstName, String lastName) {
//        return iamContextFacade.fetchTeacherIdByFullName(firstName, lastName);
//    }
}
