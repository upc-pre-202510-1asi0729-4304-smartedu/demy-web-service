package com.smartedu.demy.platform.enrollment.interfaces.rest.controllers;

import com.smartedu.demy.platform.enrollment.domain.model.commands.DeleteStudentCommand;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetAllStudentsQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetStudentByDniQuery;
import com.smartedu.demy.platform.enrollment.domain.model.queries.GetStudentByIdQuery;
import com.smartedu.demy.platform.enrollment.domain.services.StudentCommandService;
import com.smartedu.demy.platform.enrollment.domain.services.StudentQueryService;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.CreateStudentResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.StudentResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.UpdateStudentResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.transform.CreateStudentCommandFromResourceAssembler;
import com.smartedu.demy.platform.enrollment.interfaces.rest.transform.StudentResourceFromEntityAssembler;
import com.smartedu.demy.platform.enrollment.interfaces.rest.transform.UpdateStudentCommandFromResourceAssembler;
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
 * REST Controller for managing students.
 * Provides endpoints to create, retrieve, update, and delete student records.
 */
@RestController
@RequestMapping(value = "/api/v1/students", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Available Student Endpoints")
public class StudentsController {

    private final StudentCommandService studentCommandService;
    private final StudentQueryService studentQueryService;

    /**
     * Constructs the StudentsController with the required services.
     *
     * @param studentCommandService service to handle student commands (create, update, delete)
     * @param studentQueryService service to handle student queries (fetch students)
     */
    public StudentsController(StudentCommandService studentCommandService, StudentQueryService studentQueryService) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
    }

    /**
     * Creates a new student.
     *
     * @param resource the request body containing student data
     * @return ResponseEntity with the created student resource or an error status
     */
    @PostMapping
    @Operation(summary = "Create a new student", description = "Create a new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Student with given DNI already exists")
    })
    public ResponseEntity<StudentResource> createStudent(@RequestBody CreateStudentResource resource) {
        var createCommand = CreateStudentCommandFromResourceAssembler.toCommandFromResource(resource);
        var studentId = studentCommandService.handle(createCommand);
        if (studentId == null || studentId == 0L) return ResponseEntity.badRequest().build();

        var getByIdQuery = new GetStudentByIdQuery(studentId);
        var student = studentQueryService.handle(getByIdQuery);
        if (student.isEmpty()) return ResponseEntity.notFound().build();

        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return new ResponseEntity<>(studentResource, HttpStatus.CREATED);
    }

    /**
     * Retrieves all registered students.
     *
     * @return a list of student resources or not found status if empty
     */
    @GetMapping
    @Operation(summary = "Get all students", description = "Get all registered students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students found"),
            @ApiResponse(responseCode = "404", description = "No students found")
    })
    public ResponseEntity<List<StudentResource>> getAllStudents() {
        var students = studentQueryService.handle(new GetAllStudentsQuery());
        if (students.isEmpty()) return ResponseEntity.notFound().build();

        var resources = students.stream()
                .map(StudentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves a specific student by their ID.
     *
     * @param studentId the ID of the student
     * @return the student resource or not found status
     */
    @GetMapping("/{studentId}")
    @Operation(summary = "Get student by ID", description = "Retrieve a student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResource> getStudentById(@PathVariable Long studentId) {
        var student = studentQueryService.handle(new GetStudentByIdQuery(studentId));
        if (student.isEmpty()) return ResponseEntity.notFound().build();

        var resource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Retrieves a student by their DNI.
     *
     * @param dni the DNI of the student
     * @return the student resource or not found status
     */
    @GetMapping("/dni/{dni}")
    @Operation(summary = "Get student by DNI", description = "Retrieve a student by their DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResource> getStudentByDni(@PathVariable String dni) {
        var student = studentQueryService.handle(new GetStudentByDniQuery(dni));
        if (student.isEmpty()) return ResponseEntity.notFound().build();

        var resource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Updates an existing student's information.
     *
     * @param studentId the ID of the student to update
     * @param resource the request body with updated student data
     * @return the updated student resource or not found status
     */
    @PutMapping("/{studentId}")
    @Operation(summary = "Update student", description = "Update a student's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResource> updateStudent(@PathVariable Long studentId, @RequestBody UpdateStudentResource resource) {
        var updateCommand = UpdateStudentCommandFromResourceAssembler.toCommandFromResource(studentId, resource);
        var updatedStudent = studentCommandService.handle(updateCommand);
        if (updatedStudent.isEmpty()) return ResponseEntity.notFound().build();

        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(updatedStudent.get());
        return ResponseEntity.ok(studentResource);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param studentId the ID of the student to delete
     * @return a message indicating the student was successfully deleted
     */
    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete student", description = "Delete a student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId) {
        var deleteCommand = new DeleteStudentCommand(studentId);
        studentCommandService.handle(deleteCommand);
        return ResponseEntity.ok(new MessageResource("Student successfully deleted"));
    }
}
