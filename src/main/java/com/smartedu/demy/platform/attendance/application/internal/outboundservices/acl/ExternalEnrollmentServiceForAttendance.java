package com.smartedu.demy.platform.attendance.application.internal.outboundservices.acl;

import com.smartedu.demy.platform.enrollment.interfaces.acl.EnrollmentsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @name ExternalEnrollmentServiceForAttendance
 * @summary
 * This ACL (Anti-Corruption Layer) service acts as a boundary between the Attendance bounded context
 * and the Enrollment bounded context. It allows Attendance to fetch student information (e.g., full name)
 * without introducing direct dependencies or leaking enrollment domain concepts.
 *
 * <p>
 * Used to retrieve the student's full name given their DNI by delegating to the EnrollmentsContextFacade.
 * </p>
 *
 * @since 1.0
 */
@Service
public class ExternalEnrollmentServiceForAttendance {

    private final EnrollmentsContextFacade enrollmentsContextFacade;
    /**
     * Constructs the ACL service with the given enrollment context facade.
     *
     * @param enrollmentsContextFacade the facade to communicate with the Enrollment bounded context
     */
    public ExternalEnrollmentServiceForAttendance(EnrollmentsContextFacade enrollmentsContextFacade) {
        this.enrollmentsContextFacade = enrollmentsContextFacade;
    }

    /**
     * Fetches the student's full name given their DNI by calling the Enrollment bounded context.
     *
     * @param dni the student's unique identifier
     * @return an Optional containing the student's full name if available; otherwise, an empty Optional
     */
    public Optional<String> fetchStudentNameByDni(String dni) {
        var studentName = enrollmentsContextFacade.fetchStudentFullNameByDni(dni);
        return Objects.equals(studentName, "") ? Optional.empty() : Optional.of(studentName);
    }
}