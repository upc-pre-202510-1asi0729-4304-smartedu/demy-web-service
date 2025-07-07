package com.smartedu.demy.platform.scheduling.application.internal.outboundservices.acl;

import com.smartedu.demy.platform.iam.interfaces.acl.IamContextFacade;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * External IAM Service
 * <p>This class provides a service layer for interacting with the IAM context through the Anti-Corruption Layer (ACL) pattern. It handles teacher identification and validation operations.</p>
 */
@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    /**
     * Constructor that initializes the service with the required IAM context facade.
     * @param iamContextFacade The IAM context facade for accessing IAM operations.
     */
    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * This method is used to fetch a teacher ID by their full name using the IAM ACL.
     * @param firstName The first name of the teacher.
     * @param lastName The last name of the teacher.
     * @return An optional with the teacher's user ID if found and valid, otherwise an empty optional.
     * @see IamContextFacade
     * @see UserId
     */
    public Optional<UserId> fetchTeacherIdByFullName(String firstName, String lastName) {
        var teacherId = iamContextFacade.fetchTeacherIdByFullName(firstName, lastName);
        if (teacherId == null || teacherId == 0L) return Optional.empty();
        return Optional.of(new UserId(teacherId));
    }

}
