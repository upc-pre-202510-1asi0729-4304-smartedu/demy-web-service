package com.smartedu.demy.platform.attendance.interfaces.rest.controllers;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.services.ClassSessionCommandService;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.ClassSessionResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.CreateClassSessionResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.transform.ClassSessionResourceFromEntityAssembler;
import com.smartedu.demy.platform.attendance.interfaces.rest.transform.CreateClassSessionCommandFromResourceAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Constructor for ClassSessionController.
     * @param classSessionCommandService Class sessions command service
     * @since 1.0
     * @see ClassSessionCommandService
     */
    public ClassSessionController(ClassSessionCommandService classSessionCommandService) {
        this.classSessionCommandService = classSessionCommandService;
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
}
