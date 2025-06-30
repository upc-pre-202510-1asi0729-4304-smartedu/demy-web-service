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

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountResource> getUserAccountById(@PathVariable Long id) {
        return queryService.handle(new GetUserAccountByIdQuery(id))
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Admin sign-up")
    @PostMapping("/admins/sign-up")
    public ResponseEntity<Map<String, Object>> signUpAdmin(@RequestBody @Valid SignUpAdminResource resource) {
        var command = SignUpAdminCommandFromResourceAssembler.toCommandFromResource(resource);
        var created = commandService.handle(command);
        var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(created);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin registered successfully");
        response.put("user", responseResource);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "User sign-in")
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody @Valid SignInResource resource) {
        var command = SignInUserAccountCommandFromResourceAssembler.toCommandFromResource(resource);
        ImmutablePair<UserAccount, String> result = commandService.handle(command);

        var userResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(result.getLeft());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("user", userResource);
        response.put("token", result.getRight());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get admin profiles")
    @GetMapping("/admins")
    public ResponseEntity<List<UserAccountResource>> getAllAdmins() {
        var data = queryService.handle(new GetAllAdminsQuery())
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get teacher profiles")
    @GetMapping("/teachers")
    public ResponseEntity<List<UserAccountResource>> getAllTeachers() {
        var data = queryService.handle(new GetAllTeachersQuery())
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Add new teacher profile")
    @PostMapping("/teachers")
    public ResponseEntity<UserAccountResource> createTeacher(@RequestBody @Valid CreateTeacherResource resource) {
        var command = CreateTeacherCommandFromResourceAssembler.toCommandFromResource(resource);
        var created = commandService.handle(command);
        var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseResource);
    }

    @Operation(summary = "Reset password")
    @PutMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordResource resource) {
        var command = ResetPasswordCommandFromResourceAssembler.toCommandFromResource(resource);
        commandService.handle(command);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Password reset successfully");
        response.put("email", resource.email());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Edit user profile")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserAccountResource resource) {

        var existing = queryService.handle(new GetUserAccountByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        // Validación opcional: asegurar coherencia del rol
        if (!existing.get().getRole().name().equalsIgnoreCase(resource.role())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Role mismatch: cannot update this user as a different role"));
        }

        var command = UpdateUserAccountCommandFromResourceAssembler.toCommandFromResource(resource);
        var updated = commandService.handle(command);
        var responseResource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(updated);

        return ResponseEntity.ok(
                Map.of(
                        "message", "User updated successfully",
                        "user", responseResource
                )
        );
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        var existing = queryService.handle(new GetUserAccountByIdQuery(id));
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        // Opcional: validación de rol para evitar borrar admins si no quieres
        if (existing.get().getRole().equals(Roles.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Admins cannot be deleted from this endpoint"));
        }

        commandService.handle(new DeleteUserAccountCommand(id));

        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
