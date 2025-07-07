package com.smartedu.demy.platform.attendance.application.internal.queryservices;


import com.smartedu.demy.platform.attendance.application.internal.outboundservices.acl.ExternalEnrollmentServiceForAttendance;
import com.smartedu.demy.platform.attendance.domain.model.entities.AttendanceRecord;
import com.smartedu.demy.platform.attendance.domain.model.queries.GetAttendanceRecordsByDniCourseAndDateRangeQuery;
import com.smartedu.demy.platform.attendance.domain.services.ClassSessionQueryService;
import com.smartedu.demy.platform.attendance.infrastructure.persistence.jpa.ClassSessionRepository;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @name ClassSessionQueryServiceImpl
 * @summary
 * Implementation of the ClassSessionQueryService that retrieves attendance records
 * filtered by student DNI, course, and date range. It enriches the results by integrating
 * student names via the Enrollment bounded context (using an ACL).
 *
 * <p>
 * This service acts as an application layer component coordinating persistence queries
 * and inter-context communication.
 * </p>
 *
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class ClassSessionQueryServiceImpl implements ClassSessionQueryService {

    private final ClassSessionRepository classSessionRepository;
    private final ExternalEnrollmentServiceForAttendance externalEnrollmentServiceForAttendance;
    /**
     * Constructs the query service implementation with required dependencies.
     *
     * @param classSessionRepository the repository to access ClassSession aggregates
     * @param externalEnrollmentServiceForAttendance the ACL service to fetch student names from Enrollment context
     */
    public ClassSessionQueryServiceImpl(
            ClassSessionRepository classSessionRepository,
            ExternalEnrollmentServiceForAttendance externalEnrollmentServiceForAttendance
    ) {
        this.classSessionRepository = classSessionRepository;
        this.externalEnrollmentServiceForAttendance = externalEnrollmentServiceForAttendance;
    }

    /**
     * Handles the query to retrieve attendance records for a specific student,
     * filtered by course and date range. It also enriches each attendance record
     * with the student's full name fetched from the Enrollment bounded context.
     *
     * <p>
     * Steps performed:
     * <ul>
     *     <li>Finds all class sessions matching the course and date range.</li>
     *     <li>Filters attendance records by the given DNI.</li>
     *     <li>Enriches records with student name via the ACL service.</li>
     *     <li>Returns a flattened list of filtered and enriched attendance records.</li>
     * </ul>
     * </p>
     *
     * @param query the query containing student DNI, course ID, and date range
     * @return a list of matching and enriched attendance records
     *
     * @see GetAttendanceRecordsByDniCourseAndDateRangeQuery
     */
    @Override
    public List<AttendanceRecord> handle(GetAttendanceRecordsByDniCourseAndDateRangeQuery query) {

        //  Buscar todas las sesiones que coincidan con el curso y rango de fechas
        var sessions = classSessionRepository.findByCourseIdAndDateBetween(
                new CourseId(query.courseId()),
                query.startDate(),
                query.endDate()
        );

        //  Filtrar y enriquecer resultados
        var filteredSessions = sessions.stream()
                .map(session -> {
                    // Filtrar solo los attendance records con el dni buscado
                    var filteredAttendance = session.getAttendance().stream()
                            .filter(record -> record.getDni().equals(query.dni()))
                            .toList();

                    // Enriquecer cada record con el nombre del estudiante
                    filteredAttendance.forEach(record ->
                            externalEnrollmentServiceForAttendance
                                    .fetchStudentNameByDni(record.getDni().dni())
                                    .ifPresent(record::setStudentName)
                    );

                    // Reemplazar los registros filtrados en la sesión
                    session.setAttendance(filteredAttendance);

                    return session;
                })
                // Excluir sesiones vacías (sin registros para ese DNI)
                .filter(session -> !session.getAttendance().isEmpty())
                .toList();

        // 3 Retornar sesiones ya filtradas y enriquecidas
        return filteredSessions.stream()
                .flatMap(session -> session.getAttendance().stream())
                .toList();

    }


}
