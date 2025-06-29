package com.smartedu.demy.platform.enrollment.interfaces.rest.controllers;

import com.smartedu.demy.platform.enrollment.domain.model.commands.DeleteAcademicPeriodCommand;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAcademicPeriodByIdQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllAcademicPeriodsQuery;
import com.smartedu.demy.platform.enrollment.domain.services.AcademicPeriodCommandService;
import com.smartedu.demy.platform.enrollment.domain.services.AcademicPeriodQueryService;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.AcademicPeriodResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.CreateAcademicPeriodResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.UpdateAcademicPeriodResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.transform.AcademicPeriodResourceFromEntityAssembler;
import com.smartedu.demy.platform.enrollment.interfaces.rest.transform.CreateAcademicPeriodCommandFromResourceAssembler;
import com.smartedu.demy.platform.enrollment.interfaces.rest.transform.UpdateAcademicPeriodCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/academic-periods", produces = APPLICATION_JSON_VALUE)
@Tag(name = "AcademicPeriods", description = "Available Academic Periods Endpoints")
public class    AcademicPeriodController {
    private final AcademicPeriodCommandService academicPeriodCommandService;
    private final AcademicPeriodQueryService academicPeriodQueryService;

    public AcademicPeriodController(AcademicPeriodCommandService academicPeriodCommandService, AcademicPeriodQueryService academicPeriodQueryService) {
        this.academicPeriodCommandService = academicPeriodCommandService;
        this.academicPeriodQueryService = academicPeriodQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new academic period", description = "Create a new academic period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Academic period created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Academic Period not found")})
public ResponseEntity<AcademicPeriodResource> createAcademicPeriod(@RequestBody CreateAcademicPeriodResource resource) {
        var createAcademicPeriodCommand = CreateAcademicPeriodCommandFromResourceAssembler.toCommandFromResource(resource);
        var academicPeriodId = academicPeriodCommandService.handle(createAcademicPeriodCommand);
        if (academicPeriodId == null || academicPeriodId == 0L) return ResponseEntity.badRequest().build();
        var getAcademicPeriodById = new GetAcademicPeriodByIdQuery(academicPeriodId);
        var academicPeriod = academicPeriodQueryService.handle(getAcademicPeriodById);
        if (academicPeriod.isEmpty()) return ResponseEntity.notFound().build();
        var academicPeriodEntity = academicPeriod.get();
        var academicPeriodResource = AcademicPeriodResourceFromEntityAssembler.toResourceFromEntity(academicPeriodEntity);
        return new ResponseEntity<>(academicPeriodResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all academic periods", description = "Get all academic periods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Academic periods found"),
            @ApiResponse(responseCode = "404", description = "No academic periods found")})
    public ResponseEntity<List<AcademicPeriodResource>> getAllAcademicPeriods() {
        var academicPeriods = academicPeriodQueryService.handle(new GetAllAcademicPeriodsQuery());
        if (academicPeriods.isEmpty()) return ResponseEntity.notFound().build();
        var academicPeriodResources = academicPeriods.stream()
                .map(AcademicPeriodResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(academicPeriodResources);
    }

    @PutMapping("/{academicPeriodId}")
    @Operation(summary = "Update academic period", description = "Update academic period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Academic period updated"),
            @ApiResponse(responseCode = "404", description = "Academic period not found")})
    public ResponseEntity<AcademicPeriodResource> updateAcademicPeriod(@PathVariable Long academicPeriodId, @RequestBody UpdateAcademicPeriodResource resource) {
        var updateAcademicPeriodCommand = UpdateAcademicPeriodCommandFromResourceAssembler.toCommandFromResource(academicPeriodId, resource);
        var updatedAcademicPeriod = academicPeriodCommandService.handle(updateAcademicPeriodCommand);
        if (updatedAcademicPeriod.isEmpty()) return ResponseEntity.notFound().build();
        var updatedAcademicPeriodEntity = updatedAcademicPeriod.get();
        var updatedAcademicPeriodResource = AcademicPeriodResourceFromEntityAssembler.toResourceFromEntity(updatedAcademicPeriodEntity);
        return ResponseEntity.ok(updatedAcademicPeriodResource);
    }

    @DeleteMapping("/{academicPeriodId}")
    @Operation(summary = "Delete academic period", description = "Delete academic period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Academic period deleted"),
            @ApiResponse(responseCode = "404", description = "Academic period not found")})
    public ResponseEntity<?> deleteAcademicPeriod(@PathVariable Long academicPeriodId) {
        var deleteAcademicPeriodCommand = new DeleteAcademicPeriodCommand(academicPeriodId);
        academicPeriodCommandService.handle(deleteAcademicPeriodCommand);
        return ResponseEntity.ok("Academic period with given id successfully deleted");
    }
}
