
package com.smartedu.demy.platform.scheduling.interfaces.rest.controller;

import com.smartedu.demy.platform.scheduling.domain.model.commands.DeleteClassroomCommand;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllClassroomsQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetClassroomByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.ClassroomCommandService;
import com.smartedu.demy.platform.scheduling.domain.services.ClassroomQueryService;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.ClassroomResource;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.CreateClassroomResource;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.UpdateClassroomResource;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.ClassroomResourceFromEntityAssembler;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.CreateClassroomCommandFromResourceAssembler;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.UpdateClassroomCommandFromResourceAssembler;
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


@RestController
@RequestMapping(value = "/api/v1/classrooms", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Classrooms", description = "Classroom Management Endpoints")
public class ClassroomController {

    private final ClassroomCommandService classroomCommandService;
    private final ClassroomQueryService classroomQueryService;

    public ClassroomController(ClassroomCommandService classroomCommandService, ClassroomQueryService classroomQueryService) {
        this.classroomCommandService = classroomCommandService;
        this.classroomQueryService = classroomQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new classroom", description = "Create a new classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Classroom created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })
    public ResponseEntity<ClassroomResource> createClassroom(@RequestBody CreateClassroomResource resource) {
        // Convierte el recurso en un comando de creación de aula
        var createClassroomCommand = CreateClassroomCommandFromResourceAssembler.toCommandFromResource(resource);

        // Maneja el comando para crear el aula (devuelve el ID del aula o 0L si hay un error)
        var classroomId = classroomCommandService.handle(createClassroomCommand);

        // Verifica si el ID del aula es 0L, lo que puede indicar un error
        if (classroomId == null || classroomId == 0L ) {
            return ResponseEntity.badRequest().build();
        }

        // Consulta el aula por ID usando el ID recibido
        var getClassroomByIdQuery = new GetClassroomByIdQuery(classroomId);
        var classroom = classroomQueryService.handle(getClassroomByIdQuery);

        // Si no se encuentra el aula, devuelve un 404
        if (classroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var classroomEntity = classroom.get();

        // Convierte el aula en un recurso para devolverlo
        var classroomResource = ClassroomResourceFromEntityAssembler.toResourceFromEntity(classroomEntity);

        // Devuelve el recurso con el código de estado 201 (creado)
        return new ResponseEntity<>(classroomResource, HttpStatus.CREATED);
    }


    @GetMapping
    @Operation(summary = "Get all classrooms", description = "Get all classrooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classrooms found"),
            @ApiResponse(responseCode = "404", description = "No classrooms found")
    })
    public ResponseEntity<List<ClassroomResource>> getAllClassrooms() {
        var getAllClassroomsQuery = new GetAllClassroomsQuery();
        var classrooms = classroomQueryService.handle(getAllClassroomsQuery);
        if (classrooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var classroomResources = classrooms.stream()
                .map(ClassroomResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(classroomResources);
    }

    @GetMapping("/{classroomId}")
    @Operation(summary = "Get classroom by ID", description = "Get a classroom by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom found"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })
    public ResponseEntity<ClassroomResource> getClassroomById(@PathVariable Long classroomId) {
        var getClassroomByIdQuery = new GetClassroomByIdQuery(classroomId);
        var classroom = classroomQueryService.handle(getClassroomByIdQuery);
        if (classroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var classroomResource = ClassroomResourceFromEntityAssembler.toResourceFromEntity(classroom.get());
        return ResponseEntity.ok(classroomResource);
    }

    @PutMapping("/{classroomId}")
    @Operation(summary = "Update a classroom by ID", description = "Update a classroom by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })
    public ResponseEntity<ClassroomResource> updateClassroom(@PathVariable Long classroomId, @RequestBody UpdateClassroomResource resource) {
        var updateClassroomCommand = UpdateClassroomCommandFromResourceAssembler.toCommandFromResource(classroomId, resource);
        var updatedClassroom = classroomCommandService.handle(updateClassroomCommand);
        if (updatedClassroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedClassroomEntity = updatedClassroom.get();
        var classroomResource = ClassroomResourceFromEntityAssembler.toResourceFromEntity(updatedClassroomEntity);
        return ResponseEntity.ok(classroomResource);
    }

    @DeleteMapping("/{classroomId}")
    @Operation(summary = "Delete a classroom by ID", description = "Delete a classroom by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })
    public ResponseEntity<?> deleteClassroom(@PathVariable Long classroomId) {
        var deleteClassroomCommand = new DeleteClassroomCommand(classroomId);
        classroomCommandService.handle(deleteClassroomCommand);
        return ResponseEntity.ok(new MessageResource("Classroom deleted successfully"));
    }
}

