package com.smartedu.demy.platform.billing.application.internal.outboundservices.acl;

import com.smartedu.demy.platform.enrollment.interfaces.acl.EnrollmentsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ExternalEnrollmentService {
    private final EnrollmentsContextFacade enrollmentsContextFacade;

    public ExternalEnrollmentService(EnrollmentsContextFacade enrollmentsContextFacade) {
        this.enrollmentsContextFacade = enrollmentsContextFacade;
    }

    public Optional<String> fetchStudentNameByDni(String dni) {
        var studentName = enrollmentsContextFacade.fetchStudentFullNameByDni(dni);
        return Objects.equals(studentName, "") ? Optional.empty() : Optional.of(studentName);
    }
}
