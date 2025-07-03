package com.smartedu.demy.platform.scheduling.interfaces.rest.controller;

import com.smartedu.demy.platform.scheduling.domain.model.commands.*;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllWeeklySchedulesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleCommandService;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleQueryService;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.*;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.*;
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
@RequestMapping(value = "/api/v1/weekly-schedules", produces = APPLICATION_JSON_VALUE)
@Tag(name = "WeeklySchedules", description = "Weekly Schedule Management Endpoints")
public class WeeklyScheduleController {

    private final WeeklyScheduleCommandService weeklyScheduleCommandService;
    private final WeeklyScheduleQueryService weeklyScheduleQueryService;

    public WeeklyScheduleController(WeeklyScheduleCommandService weeklyScheduleCommandService, WeeklyScheduleQueryService weeklyScheduleQueryService) {
        this.weeklyScheduleCommandService = weeklyScheduleCommandService;
        this.weeklyScheduleQueryService = weeklyScheduleQueryService;
    }

    // Método para crear un horario semanal
    @PostMapping
    @Operation(summary = "Create a new weekly schedule", description = "Create a new weekly schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Weekly schedule created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Weekly schedule not found")
    })
    public ResponseEntity<WeeklyScheduleResource> createWeeklySchedule(@RequestBody CreateWeeklyScheduleResource resource) {
        var createWeeklyScheduleCommand = CreateWeeklyScheduleCommandFromResourceAssembler.toCommandFromResource(resource);
        var weeklyScheduleId = weeklyScheduleCommandService.handle(createWeeklyScheduleCommand);

        if (weeklyScheduleId == null || weeklyScheduleId == 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getWeeklyScheduleByIdQuery = new GetWeeklyScheduleByIdQuery(weeklyScheduleId);
        var weeklySchedule = weeklyScheduleQueryService.handle(getWeeklyScheduleByIdQuery);

        if (weeklySchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var weeklyScheduleResource = WeeklyScheduleResourceFromEntityAssembler.toResourceFromEntity(weeklySchedule.get());
        return new ResponseEntity<>(weeklyScheduleResource, HttpStatus.CREATED);
    }

    // Método para obtener todos los horarios semanales
    @GetMapping
    @Operation(summary = "Get all weekly schedules", description = "Get all weekly schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weekly schedules found"),
            @ApiResponse(responseCode = "404", description = "No weekly schedules found")
    })
    public ResponseEntity<List<WeeklyScheduleResource>> getAllWeeklySchedules() {
        var getAllWeeklySchedulesQuery = new GetAllWeeklySchedulesQuery();
        var weeklySchedules = weeklyScheduleQueryService.handle(getAllWeeklySchedulesQuery);

        if (weeklySchedules.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var weeklyScheduleResources = weeklySchedules.stream()
                .map(WeeklyScheduleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(weeklyScheduleResources);
    }

    // Método para obtener un horario semanal por ID
    @GetMapping("/{weeklyScheduleId}")
    @Operation(summary = "Get weekly schedule by ID", description = "Get a weekly schedule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weekly schedule found"),
            @ApiResponse(responseCode = "404", description = "Weekly schedule not found")
    })
    public ResponseEntity<WeeklyScheduleResource> getWeeklyScheduleById(@PathVariable Long weeklyScheduleId) {
        var getWeeklyScheduleByIdQuery = new GetWeeklyScheduleByIdQuery(weeklyScheduleId);
        var weeklySchedule = weeklyScheduleQueryService.handle(getWeeklyScheduleByIdQuery);

        if (weeklySchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var weeklyScheduleResource = WeeklyScheduleResourceFromEntityAssembler.toResourceFromEntity(weeklySchedule.get());
        return ResponseEntity.ok(weeklyScheduleResource);
    }

    // Método para actualizar un horario semanal por ID
    @PutMapping("/{weeklyScheduleId}")
    @Operation(summary = "Update a weekly schedule by ID", description = "Update a weekly schedule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weekly schedule updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Weekly schedule not found")
    })
    public ResponseEntity<WeeklyScheduleResource> updateWeeklySchedule(@PathVariable Long weeklyScheduleId, @RequestBody UpdateWeeklyScheduleNameResource resource) {
        var updateWeeklyScheduleCommand = UpdateWeeklyScheduleNameCommandFromResourceAssembler.toCommandFromResource(weeklyScheduleId, resource);
        var updatedWeeklySchedule = weeklyScheduleCommandService.handle(updateWeeklyScheduleCommand);

        if (updatedWeeklySchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedWeeklyScheduleEntity = updatedWeeklySchedule.get();
        var updatedWeeklyScheduleResource = WeeklyScheduleResourceFromEntityAssembler.toResourceFromEntity(updatedWeeklyScheduleEntity);
        return ResponseEntity.ok(updatedWeeklyScheduleResource);
    }

//    // Método para eliminar un horario semanal por ID
//    @DeleteMapping("/{weeklyScheduleId}")
//    @Operation(summary = "Delete a weekly schedule by ID", description = "Delete a weekly schedule by ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Weekly schedule deleted successfully"),
//            @ApiResponse(responseCode = "404", description = "Weekly schedule not found")
//    })
//    public ResponseEntity<?> deleteWeeklySchedule(@PathVariable Long weeklyScheduleId) {
//        var deleteWeeklyScheduleCommand = new DeleteWeeklyScheduleCommand(weeklyScheduleId);
//        weeklyScheduleCommandService.handle(deleteWeeklyScheduleCommand);
//        return ResponseEntity.ok("Weekly schedule deleted successfully");
//    }

    // Método para agregar un horario a un horario semanal
    @PostMapping("/{weeklyScheduleId}/schedules")
    @Operation(summary = "Add Schedule to Weekly Schedule", description = "Add a new schedule to an existing weekly schedule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The schedule was added to the weekly schedule."),
            @ApiResponse(responseCode = "400", description = "The schedule was not added."),
            @ApiResponse(responseCode = "404", description = "The weekly schedule was not found.")
    })
    public ResponseEntity<WeeklyScheduleResource> addScheduleToWeeklySchedule(@PathVariable Long weeklyScheduleId, @RequestBody AddScheduleToWeeklyResource resource) {
        var addScheduleToWeeklyCommand = AddScheduleToWeeklyCommandFromResourceAssembler.toCommandFromResource(weeklyScheduleId, resource);
        var updatedWeeklySchedule = weeklyScheduleCommandService.handle(addScheduleToWeeklyCommand);

        if (updatedWeeklySchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedWeeklyScheduleEntity = updatedWeeklySchedule.get();
        var updatedWeeklyScheduleResource = WeeklyScheduleResourceFromEntityAssembler.toResourceFromEntity(updatedWeeklyScheduleEntity);
        return ResponseEntity.ok(updatedWeeklyScheduleResource);
    }

    // Método para eliminar un horario de un horario semanal
    @DeleteMapping("/{weeklyScheduleId}/schedules/{scheduleId}")
    @Operation(summary = "Remove Schedule from Weekly Schedule", description = "Remove a schedule from a weekly schedule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The schedule was removed from the weekly schedule."),
            @ApiResponse(responseCode = "400", description = "The schedule was not removed."),
            @ApiResponse(responseCode = "404", description = "The weekly schedule or schedule was not found.")
    })
    public ResponseEntity<WeeklyScheduleResource> removeScheduleFromWeeklySchedule(@PathVariable Long weeklyScheduleId, @PathVariable Long scheduleId) {
        var removeScheduleFromWeeklyCommand = new RemoveScheduleFromWeeklyCommand(weeklyScheduleId, scheduleId);
        var updatedWeeklySchedule = weeklyScheduleCommandService.handle(removeScheduleFromWeeklyCommand);
        if (updatedWeeklySchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedWeeklyScheduleEntity = updatedWeeklySchedule.get();
        var updatedWeeklyScheduleResource = WeeklyScheduleResourceFromEntityAssembler.toResourceFromEntity(updatedWeeklyScheduleEntity);
        return ResponseEntity.ok(updatedWeeklyScheduleResource);
    }

//    // Método para eliminar un horario de un horario semanal
//    @DeleteMapping("/{weeklyScheduleId}/schedules/{scheduleId}")
//    @Operation(summary = "Remove Schedule from Weekly Schedule", description = "Remove a schedule from a weekly schedule.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The schedule was removed from the weekly schedule."),
//            @ApiResponse(responseCode = "400", description = "The schedule was not removed."),
//            @ApiResponse(responseCode = "404", description = "The weekly schedule or schedule was not found.")
//    })
//    public ResponseEntity<WeeklyScheduleResource> removeScheduleFromWeeklySchedule(@PathVariable Long weeklyScheduleId, @PathVariable Long scheduleId) {
//        var removeScheduleFromWeeklyCommand = new RemoveScheduleFromWeeklyCommand(weeklyScheduleId, scheduleId);
//        var updatedWeeklySchedule = weeklyScheduleCommandService.handle(removeScheduleFromWeeklyCommand);
//        if (updatedWeeklySchedule.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        var updatedWeeklyScheduleEntity = updatedWeeklySchedule.get();
//        var updatedWeeklyScheduleResource = WeeklyScheduleResourceFromEntityAssembler.toResourceFromEntity(updatedWeeklyScheduleEntity);
//        return ResponseEntity.ok(updatedWeeklyScheduleResource);
//    }
}
