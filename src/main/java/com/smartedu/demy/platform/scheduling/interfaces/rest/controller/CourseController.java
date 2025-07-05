package com.smartedu.demy.platform.scheduling.interfaces.rest.controller;

import com.smartedu.demy.platform.scheduling.domain.model.commands.DeleteCourseCommand;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetAllCoursesQuery;
import com.smartedu.demy.platform.scheduling.domain.model.queries.GetCourseByIdQuery;
import com.smartedu.demy.platform.scheduling.domain.services.CourseCommandService;
import com.smartedu.demy.platform.scheduling.domain.services.CourseQueryService;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.CourseResource;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.CreateCourseResource;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.UpdateCourseResource;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.CourseResourceFromEntityAssembler;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.CreateCourseCommandFromResourceAssembler;
import com.smartedu.demy.platform.scheduling.interfaces.rest.transform.UpdateCourseCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/courses", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses", description = "Course Management Endpoints")
public class CourseController {

    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;

    public CourseController(CourseCommandService courseCommandService, CourseQueryService courseQueryService) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Create a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResource> createCourse(@RequestBody CreateCourseResource resource) {
        // Convierte el recurso en un comando de creación de curso
        var createCourseCommand = CreateCourseCommandFromResourceAssembler.toCommandFromResource(resource);

        // Maneja el comando para crear el curso (devuelve el ID del curso o 0L si hay un error)
        var courseId = courseCommandService.handle(createCourseCommand);

        // Verifica si el ID del curso es 0L, lo que puede indicar un error
        if (courseId == null || courseId == 0L ) {
            return ResponseEntity.badRequest().build();
        }

        // Consulta el curso por ID usando el ID recibido
        var getCourseByIdQuery = new GetCourseByIdQuery(courseId);
        var course = courseQueryService.handle(getCourseByIdQuery);

        // Si no se encuentra el curso, devuelve un 404
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var courseEntity = course.get();

        // Convierte el curso en un recurso para devolverlo
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(courseEntity);

        // Devuelve el recurso con el código de estado 201 (creado)
        return new ResponseEntity<>(courseResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Get all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses found"),
            @ApiResponse(responseCode = "404", description = "No courses found")
    })
    public ResponseEntity<List<CourseResource>> getAllCourses() {
        var getAllCoursesQuery = new GetAllCoursesQuery();
        var courses = courseQueryService.handle(getAllCoursesQuery);
        if (courses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var courseResources = courses.stream()
                .map(CourseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(courseResources);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Get course by ID", description = "Get a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResource> getCourseById(@PathVariable Long courseId) {
        var getCourseByIdQuery = new GetCourseByIdQuery(courseId);
        var course = courseQueryService.handle(getCourseByIdQuery);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(course.get());
        return ResponseEntity.ok(courseResource);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update a course by ID", description = "Update a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResource> updateCourse(@PathVariable Long courseId, @RequestBody UpdateCourseResource resource) {
        var updateCourseCommand = UpdateCourseCommandFromResourceAssembler.toCommandFromResource(courseId, resource);
        var updatedCourse = courseCommandService.handle(updateCourseCommand);
        if (updatedCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedCourseEntity = updatedCourse.get();
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(updatedCourseEntity);
        return ResponseEntity.ok(courseResource);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Delete a course by ID", description = "Delete a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        var deleteCourseCommand = new DeleteCourseCommand(courseId);
        courseCommandService.handle(deleteCourseCommand);
        return ResponseEntity.ok(new MessageResource("Course deleted successfully"));
    }
}
