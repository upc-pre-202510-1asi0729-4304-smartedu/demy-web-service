package com.smartedu.demy.platform.attendance.interfaces.rest.controllers;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.queries.GetAttendanceRecordsByDniCourseAndDateRangeQuery;
import com.smartedu.demy.platform.attendance.domain.services.ClassSessionCommandService;
import com.smartedu.demy.platform.attendance.domain.services.ClassSessionQueryService;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.ClassSessionReportResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.ClassSessionResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.CreateClassSessionResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.AttendanceReportResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.transform.ClassSessionReportResourceFromEntityAssembler;
import com.smartedu.demy.platform.attendance.interfaces.rest.transform.ClassSessionResourceFromEntityAssembler;
import com.smartedu.demy.platform.attendance.interfaces.rest.transform.CreateClassSessionCommandFromResourceAssembler;
import com.smartedu.demy.platform.attendance.interfaces.rest.transform.AttendanceReportFromEntityAssembler;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for class sessions.
 * @summary
 * This class provides REST endpoints for class sessions
 * @since 1.0
 */
@RestController
@RequestMapping(value="/api/v1/class-sessions", produces = APPLICATION_JSON_VALUE)
@Tag(name="Class Sessions", description="Endpoints for class sessions")
public class ClassSessionController {
    private final ClassSessionCommandService classSessionCommandService;
    private final ClassSessionQueryService classSessionQueryService;


    /**
     * Constructor for ClassSessionController.
     * @param classSessionCommandService Class sessions command service
     * @since 1.0
     * @see ClassSessionCommandService
     */
    public ClassSessionController(ClassSessionCommandService classSessionCommandService, ClassSessionQueryService classSessionQueryService) {
        this.classSessionCommandService = classSessionCommandService;
        this.classSessionQueryService = classSessionQueryService;

    }
    /**
     * Creates a class session.
     *
     */
    @Operation(
            summary = "Create a class session",
            description = "Creates a class session with the provided CourseId, date and attendance record"
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description="Class session created"),
            @ApiResponse(responseCode= "400", description="Bad request")
    })
    @PostMapping
    public ResponseEntity<ClassSessionResource> createClassSession(@RequestBody CreateClassSessionResource resource){
        Optional<ClassSession> classSession = classSessionCommandService
                .handle(CreateClassSessionCommandFromResourceAssembler.toCommandFromResource(resource));
        return classSession.map(source -> new ResponseEntity<>(ClassSessionResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @Operation(
            summary = "Get attendance report by DNI, CourseId and Date Range",
            description = "Returns the attendance records grouped in class sessions filtered by DNI, course and date range."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attendance report retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/report")
    public ResponseEntity<ClassSessionReportResource> getAttendanceReport(
            @RequestParam String dni,
            @RequestParam Long courseId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        try {
            var query = new GetAttendanceRecordsByDniCourseAndDateRangeQuery(
                    new Dni(dni),
                    courseId,
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );

            var records = classSessionQueryService.handle(query);

            var resource = ClassSessionReportResourceFromEntityAssembler.toResourceFromEntities(
                    courseId,
                    records
            );

            return ResponseEntity.ok(resource);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
