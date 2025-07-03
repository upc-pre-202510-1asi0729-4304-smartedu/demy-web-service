package com.smartedu.demy.platform.iam.interfaces.rest.controllers;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.commands.DeleteUserAccountCommand;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllAdminsQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetAllTeachersQuery;
import com.smartedu.demy.platform.iam.domain.model.queries.GetUserAccountByIdQuery;
import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.*;
import com.smartedu.demy.platform.iam.interfaces.rest.transform.*;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "UserAccount", description = "User administration endpoints")
public class UserAccountsController {

    private final UserAccountQueryService queryService;
    private final UserAccountCommandService commandService;

    public UserAccountsController(UserAccountQueryService queryService,
                                  UserAccountCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @Operation(summary = "Get user by id", description = "Retrieve a user account by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User found\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"string\"}}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAccountById(@PathVariable Long id) {
        return queryService.handle(new GetUserAccountByIdQuery(id))
                .map(user -> {
                    var resource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(user);
                    Map<String, Object> response = new LinkedHashMap<>();
                    response.put("message", "User found");
                    response.put("user", resource);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found")));
    }

    @Operation(summary = "Admin sign-up", description = "Registers a new admin account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin registered successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Admin registered successfully\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"ADMIN\"}}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Invalid input\"}"))),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Email already registered\"}")))
    })
    @PostMapping("/admins/sign-up")
    public ResponseEntity<Map<String, Object>> signUpAdmin(@RequestBody @Valid SignUpAdminResource resource) {
        try {
            var command = SignUpAdminCommandFromResourceAssembler.toCommandFromResource(resource);
            var created = commandService.handle(command);
            var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(created);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Admin registered successfully");
            response.put("user", responseResource);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            if (ex.getMessage().equalsIgnoreCase("Email already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Email already registered"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid input"));
        }
    }

    @Operation(summary = "User sign-in", description = "Authenticate user and return a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Login successful\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"string\"},\"token\": \"string\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid password", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Invalid password\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody @Valid SignInResource resource) {

        try {
            var command = SignInUserAccountCommandFromResourceAssembler.toCommandFromResource(resource);
            ImmutablePair<UserAccount, String> result = commandService.handle(command);

            var userResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(result.getLeft());

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "user", userResource,
                    "token", result.getRight()
            ));
        } catch (RuntimeException ex) {
            if (ex.getMessage().equalsIgnoreCase("Invalid password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid password"));
            }
            if (ex.getMessage().equalsIgnoreCase("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unexpected server error"));
        }
    }

    @Operation(summary = "Get admin profiles", description = "List all users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of admins retrieved")
    })
    @GetMapping("/admins")
    public ResponseEntity<List<UserAccountResource>> getAllAdmins() {
        var data = queryService.handle(new GetAllAdminsQuery())
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get teacher profiles", description = "List all users with TEACHER role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of teachers retrieved")
    })
    @GetMapping("/teachers")
    public ResponseEntity<List<UserAccountResource>> getAllTeachers() {
        var data = queryService.handle(new GetAllTeachersQuery())
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Add new teacher profile", description = "Create and register a new teacher account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teacher created successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Teacher created successfully\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"TEACHER\"}}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Invalid input\"}"))),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Email already registered\"}")))
    })

    @PostMapping("/teachers")
    //cambiar el map string object
    public ResponseEntity<Map<String, Object>> createTeacher(@RequestBody @Valid CreateTeacherResource resource) {
        var command = CreateTeacherCommandFromResourceAssembler.toCommandFromResource(resource);
        try {
            var created = commandService.handle(command);
            var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(created);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Teacher created successfully");
            response.put("user", responseResource);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            if (ex.getMessage().equalsIgnoreCase("Email already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Email already registered"));
            }
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid input"));
        }
    }

    @Operation(summary = "Reset password", description = "Allows a user to reset their password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Password reset successfully\", \"email\": \"string\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })

    @PutMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordResource resource) {
        try {
            var command = ResetPasswordCommandFromResourceAssembler.toCommandFromResource(resource);
            commandService.handle(command);

            return ResponseEntity.ok(Map.of(
                    "message", "Password reset successfully",
                    "email", resource.email()
            ));
        } catch (RuntimeException ex) {
            if (ex.getMessage().equalsIgnoreCase("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unexpected error"));
        }
    }

    @Operation(summary = "Edit user profile", description = "Update the user profile data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User updated successfully\", \"user\": {\"id\": \"string\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"string\"}}"))),
            @ApiResponse(responseCode = "403", description = "Role mismatch: cannot update this user as a different role", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Role mismatch: cannot update this user as a different role\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserAccountResource resource) {

        var existing = queryService.handle(new GetUserAccountByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        if (!existing.get().getRole().name().equalsIgnoreCase(resource.role())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Role mismatch: cannot update this user as a different role"));
        }

        var command = UpdateUserAccountCommandFromResourceAssembler.toCommandFromResource(id,resource);
        var updated = commandService.handle(command);
        var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(updated);

        return ResponseEntity.ok(
                Map.of(
                        "message", "User updated successfully",
                        "user", responseResource
                )
        );
    }

    @Operation(summary = "Delete user", description = "Deletes a non-admin user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User deleted successfully\", \"deletedUser\": {\"id\": \"string\", \"username\": \"string\"}}"))),
            @ApiResponse(responseCode = "403", description = "Admins cannot be deleted from this endpoin", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Admins cannot be deleted from this endpoint\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        var existing = queryService.handle(new GetUserAccountByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        if (existing.get().getRole().equals(Roles.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Admins cannot be deleted from this endpoint"));
        }

        commandService.handle(new DeleteUserAccountCommand(id));

        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
