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

/**
 * REST controller responsible for handling all user account-related endpoints.
 * Provides operations for registration, login, retrieval, update, and deletion of user accounts.
 * Handles both ADMIN and TEACHER roles.
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "UserAccount", description = "User administration endpoints")
public class UserAccountsController {

    /**
     * Service for querying user account information.
     */
    private final UserAccountQueryService queryService;

    /**
     * Service for executing user account commands such as registration, login, deletion, etc.
     */
    private final UserAccountCommandService commandService;

    /**
     * Constructs the controller with required services.
     * @param queryService user account query service
     * @param commandService user account command service
     */
    public UserAccountsController(UserAccountQueryService queryService,
                                  UserAccountCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    /**
     * Retrieves a user account by its unique identifier.
     *
     * @param id the ID of the user to retrieve
     * @return a {@link ResponseEntity} containing the user details and a success message,
     *         or a 404 response if the user is not found
     */
    @Operation(summary = "Get user by id", description = "Retrieve a user account by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User found\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"string\"}}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAccountById(@PathVariable Long id) {

        /**
         * Attempt to find the user by ID.
         * If the user exists, transform the domain entity into a resource DTO,
         * and return it with a success message.
         */
        return queryService.handle(new GetUserAccountByIdQuery(id))
                .map(user -> {
                    var resource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(user);
                    Map<String, Object> response = new LinkedHashMap<>();
                    response.put("message", "User found");
                    response.put("user", resource);
                    return ResponseEntity.ok(response);
                })

                /**
                 * If no user is found with the given ID, return a 404 response.
                 */
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found")));
    }

    /**
     * Registers a new admin account.
     *
     * @param resource the admin account registration details
     * @return ResponseEntity with the created user and message,
     *         or an error status in case of invalid input or conflict
     */
    @Operation(summary = "Admin sign-up", description = "Registers a new admin account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin registered successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Admin registered successfully\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"ADMIN\"}}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Invalid input\"}"))),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Email already registered\"}")))
    })
    @PostMapping("/admins/sign-up")
    public ResponseEntity<Map<String, Object>> signUpAdmin(@RequestBody @Valid SignUpAdminResource resource) {
        /**
         * Handle the sign-up process: transform the resource into a domain command,
         * execute it via the command service, then build a response with the created admin data.
         */
        try {
            var command = SignUpAdminCommandFromResourceAssembler.toCommandFromResource(resource);
            var created = commandService.handle(command);
            var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(created);

            /**
             * Build success response with user data and return 201 Created.
             */
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Admin registered successfully");
            response.put("user", responseResource);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            /**
             * If the email is already taken, return a 409 Conflict response.
             */
            if (ex.getMessage().equalsIgnoreCase("Email already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Email already registered"));
            }

            /**
             * For any other validation or conversion issue, return 400 Bad Request.
             */
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid input"));
        }
    }

    /**
     * Authenticates a user and returns a token.
     *
     * @param resource the login credentials
     * @return the authenticated user and JWT token
     */
    @Operation(summary = "User sign-in", description = "Authenticate user and return a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Login successful\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"string\"},\"token\": \"string\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid password", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Invalid password\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody @Valid SignInResource resource) {

        /**
         * Try to authenticate the user using the given credentials:
         * - Transform the request into a command object
         * - Pass it to the command service
         * - If successful, build response with user and JWT token
         * - Otherwise, handle specific errors for invalid password or user not found
         */
        try {
            var command = SignInUserAccountCommandFromResourceAssembler.toCommandFromResource(resource);
            ImmutablePair<UserAccount, String> result = commandService.handle(command);

            var userResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(result.getLeft());

            /**
             * Return a successful response containing the authenticated user and token.
             */
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "user", userResource,
                    "token", result.getRight()
            ));
        } catch (RuntimeException ex) {
            /**
             * Handle authentication errors with detailed messages.
             */
            if (ex.getMessage().equalsIgnoreCase("Invalid password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid password"));
            }
            if (ex.getMessage().equalsIgnoreCase("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            /**
             * If an unexpected error occurs during authentication, return 500.
             */
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unexpected server error"));
        }
    }

    /**
     * Retrieves a list of all users with the ADMIN role.
     *
     * @return a {@link ResponseEntity} containing a list of {@link UserAccountResource} representing admin users
     */
    @Operation(summary = "Get admin profiles", description = "List all users with ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of admins retrieved")
    })
    @GetMapping("/admins")
    public ResponseEntity<List<UserAccountResource>> getAllAdmins() {

        /**
         * Fetches all user accounts with ADMIN role using the query service,
         * and maps them to resource DTOs for the response.
         */
        var data = queryService.handle(new GetAllAdminsQuery())
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        /**
         * Returns the list of admin user resources wrapped in an HTTP 200 OK response.
         */
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get teacher profiles", description = "List all users with TEACHER role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of teachers retrieved")
    })
    @GetMapping("/teachers")
    public ResponseEntity<List<UserAccountResource>> getAllTeachers() {
        /**
         * Fetch and return all admin user accounts.
         */
        var data = queryService.handle(new GetAllTeachersQuery())
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        /**
         * Wraps the list of teacher resources in an HTTP 200 OK response.
         */
        return ResponseEntity.ok(data);
    }

    /**
     * Creates a new teacher account.
     *
     * @param resource the teacher creation request
     * @return response with created teacher
     */
    @Operation(summary = "Add new teacher profile", description = "Create and register a new teacher account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teacher created successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Teacher created successfully\", \"user\": {\"id\": \"0\", \"username\": \"string\", \"email\": \"string\", \"fullName\": \"string\", \"role\": \"TEACHER\"}}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Invalid input\"}"))),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Email already registered\"}")))
    })
    @PostMapping("/teachers")
    public ResponseEntity<Map<String, Object>> createTeacher(@RequestBody @Valid CreateTeacherResource resource) {
        /**
         * Convert the incoming resource into a command to create a new teacher.
         */
        var command = CreateTeacherCommandFromResourceAssembler.toCommandFromResource(resource);
        try {
            /**
             * Execute the command through the service layer and build the success response.
             */
            var created = commandService.handle(command);
            var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(created);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Teacher created successfully");
            response.put("user", responseResource);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            /**
             * If the email is already registered, respond with HTTP 409 Conflict.
             */
            if (ex.getMessage().equalsIgnoreCase("Email already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Email already registered"));
            }
            /**
             * If the input is invalid or another issue occurs, respond with HTTP 400 Bad Request.
             */
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid input"));
        }
    }

    /**
     * Resets a user's password.
     *
     * @param resource the password reset request
     * @return result message
     */
    @Operation(summary = "Reset password", description = "Allows a user to reset their password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Password reset successfully\", \"email\": \"string\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @PutMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordResource resource) {

        /**
         * Attempt to transform the request resource into a command and process it.
         * If the user is found, the password is updated accordingly.
         */
        try {
            var command = ResetPasswordCommandFromResourceAssembler.toCommandFromResource(resource);
            commandService.handle(command);

            /**
             * Password was reset successfully.
             * Return HTTP 200 with confirmation message and email used.
             */
            return ResponseEntity.ok(Map.of(
                    "message", "Password reset successfully",
                    "email", resource.email()
            ));
        } catch (RuntimeException ex) {
            /**
             * If the user does not exist in the system, return HTTP 404.
             */
            if (ex.getMessage().equalsIgnoreCase("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            /**
             * For any other unexpected server error, return HTTP 500.
             */
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unexpected error"));
        }
    }

    /**
     * Updates a user's profile.
     *
     * @param id user ID to update
     * @param resource updated info
     * @return updated user
     */
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

        /**
         * Try to retrieve the user by ID.
         * If not found, return 404 Not Found.
         */
        var existing = queryService.handle(new GetUserAccountByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        /**
         * Ensure the role in the request matches the user's current role.
         * Prevents unauthorized role changes.
         * If mismatch, return 403 Forbidden.
         */
        if (!existing.get().getRole().name().equalsIgnoreCase(resource.role())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Role mismatch: cannot update this user as a different role"));
        }


        /**
         * Convert the resource to an update command and handle the update.
         */
        var command = UpdateUserAccountCommandFromResourceAssembler.toCommandFromResource(id,resource);
        var updated = commandService.handle(command);

        /**
         * Transform the updated entity to a resource and return success response.
         */
        var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(updated);

        return ResponseEntity.ok(
                Map.of(
                        "message", "User updated successfully",
                        "user", responseResource
                )
        );
    }

    /**
     * Deletes a non-admin user by ID.
     *
     * @param id user ID to delete
     * @return deletion confirmation
     */
    @Operation(summary = "Delete user", description = "Deletes a non-admin user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User deleted successfully\", \"deletedUser\": {\"id\": \"string\", \"username\": \"string\"}}"))),
            @ApiResponse(responseCode = "403", description = "Admins cannot be deleted from this endpoin", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"Admins cannot be deleted from this endpoint\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User not found\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        /**
         * Attempt to retrieve the user by ID.
         * If not found, return 404 NOT FOUND.
         */
        var existing = queryService.handle(new GetUserAccountByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        /**
         * If the user has ADMIN role, reject the deletion.
         * Return 403 FORBIDDEN since admins cannot be deleted from this endpoint.
         */
        if (existing.get().getRole().equals(Roles.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Admins cannot be deleted from this endpoint"));
        }

        /**
         * Perform the deletion using the command service.
         */
        commandService.handle(new DeleteUserAccountCommand(id));

        /**
         * Return 200 OK with a confirmation message.
         */
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
