package com.smartedu.demy.platform.scheduling.interfaces.rest.controllers;

import com.smartedu.demy.platform.scheduling.domain.model.commands.*;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllWeeklySchedulesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetSchedulesByTeacherIdQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetWeeklyScheduleByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleCommandService;
import com.smartedu.demy.platform.scheduling.domain.services.WeeklyScheduleQueryService;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.*;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.*;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;
import com.smartedu.demy.platform.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing weekly schedules. It provides endpoints for creating,
 * retrieving, updating, deleting weekly schedules, and managing associated schedules.
 *
 * This controller interacts with {@link WeeklyScheduleCommandService} for handling
 * commands related to weekly schedule creation, updating, deletion, and adding/removing
 * schedules, and with {@link WeeklyScheduleQueryService} for handling queries related to
 * retrieving weekly schedules and schedules by teacher.
 */
@RestController
@RequestMapping(value = "/api/v1/weekly-schedules", produces = APPLICATION_JSON_VALUE)
@Tag(name = "WeeklySchedules", description = "Weekly Schedule Management Endpoints")
public class WeeklySchedulesController {

    private final WeeklyScheduleCommandService weeklyScheduleCommandService;
    private final WeeklyScheduleQueryService weeklyScheduleQueryService;

    public WeeklySchedulesController(WeeklyScheduleCommandService weeklyScheduleCommandService, WeeklyScheduleQueryService weeklyScheduleQueryService) {
        this.weeklyScheduleCommandService = weeklyScheduleCommandService;
        this.weeklyScheduleQueryService = weeklyScheduleQueryService;
    }

    /**
     * Creates a new weekly schedule based on the provided resource.
     *
     * @param resource The resource containing the details of the weekly schedule to be created.
     * @return A {@link ResponseEntity} with the created weekly schedule or an error response.
     */
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

    /**
     * Retrieves all weekly schedules.
     *
     * @return A {@link ResponseEntity} containing a list of weekly schedules or an error response.
     */
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

    /**
     * Retrieves a weekly schedule by its ID.
     *
     * @param weeklyScheduleId The ID of the weekly schedule to retrieve.
     * @return A {@link ResponseEntity} containing the weekly schedule resource or an error response.
     */
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

    /**
     * Updates a weekly schedule by its ID.
     *
     * @param weeklyScheduleId The ID of the weekly schedule to update.
     * @param resource The resource containing the updated details of the weekly schedule.
     * @return A {@link ResponseEntity} containing the updated weekly schedule or an error response.
     */
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

    /**
     * Deletes a weekly schedule by its ID.
     *
     * @param weeklyScheduleId The ID of the weekly schedule to delete.
     * @return A {@link ResponseEntity} indicating the deletion status.
     */
    @DeleteMapping("/{weeklyScheduleId}")
    @Operation(summary = "Delete a weekly schedule by ID", description = "Delete a weekly schedule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weekly schedule deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Weekly schedule not found")
    })
    public ResponseEntity<?> deleteWeeklySchedule(@PathVariable Long weeklyScheduleId) {
        var deleteWeeklyScheduleCommand = new DeleteWeeklyScheduleCommand(weeklyScheduleId);
        weeklyScheduleCommandService.handle(deleteWeeklyScheduleCommand);
        return ResponseEntity.ok(new MessageResource("WeeklySchedule deleted successfully"));
    }

    /**
     * Adds a schedule to an existing weekly schedule.
     *
     * @param weeklyScheduleId The ID of the weekly schedule to add the schedule to.
     * @param resource The resource containing the schedule details to be added.
     * @return A {@link ResponseEntity} containing the updated weekly schedule.
     */
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

    /**
     * Removes a schedule from a weekly schedule.
     *
     * @param weeklyScheduleId The ID of the weekly schedule.
     * @param scheduleId The ID of the schedule to remove.
     * @return A {@link ResponseEntity} containing the updated weekly schedule.
     */
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

    /**
     * Retrieves all weekly schedules for a specific teacher by their teacher ID.
     *
     * @param teacherId The ID of the teacher whose schedules to retrieve.
     * @return A {@link ResponseEntity} containing the list of schedules for the teacher.
     */
    @GetMapping("/by-teacher/{teacherId}")
    @Operation(summary = "Get Schedules by teacherId", description = "Get all schedules for a given teacherId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedules found"),
            @ApiResponse(responseCode = "404", description = "No schedules found for teacherId")
    })
    public ResponseEntity<List<ScheduleResource>> getSchedulesByTeacherId(@PathVariable Long teacherId) {
        var getSchedulesByTeacherIdQuery = new GetSchedulesByTeacherIdQuery(new UserId(teacherId));
        var schedules = weeklyScheduleQueryService.handle(getSchedulesByTeacherIdQuery);

        if (schedules == null || schedules.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var scheduleResources = schedules.stream()
                .map(ScheduleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(scheduleResources);
    }

    /**
     * Updates the details of an existing schedule.
     *
     * @param scheduleId The ID of the schedule to update.
     * @param resource The updated schedule details.
     * @return A {@link ResponseEntity} containing the updated schedule.
     */
    @PutMapping("/schedules/{scheduleId}")
    @Operation(summary = "Update a Schedule by ID", description = "Updates the classroom, start time, end time and day fields of a Schedule by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule updated successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    public ResponseEntity<?> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleResource resource) {
        var command = UpdateScheduleCommandFromResourceAssembler.toCommandFromResource(scheduleId, resource);
        var updatedScheduleOpt = weeklyScheduleCommandService.handle(command);
        if (updatedScheduleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedSchedule = updatedScheduleOpt.get();
        var scheduleResource = ScheduleResourceFromEntityAssembler.toResourceFromEntity(updatedSchedule);
        return ResponseEntity.ok(scheduleResource);
    }
}
