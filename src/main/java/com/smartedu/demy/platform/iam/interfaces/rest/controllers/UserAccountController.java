package com.smartedu.demy.platform.iam.interfaces.rest.controllers;

import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.*;
import com.smartedu.demy.platform.iam.interfaces.rest.transform.UserAccountResourceFromEntityAssembler;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class UserAccountController {

    private final UserAccountQueryService queryService;
    private final UserAccountCommandService commandService;

    public UserAccountController(UserAccountQueryService queryService,
                                 UserAccountCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    /* -----------------------------------------------------------
     * 1) USER (GENÉRICO)
     * -----------------------------------------------------------
     */

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountResource> getById(@PathVariable Long id) {
        return queryService.findById(id)
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* -----------------------------------------------------------
     * 2) ADMIN ‑ AUTH (Sign‑Up / Sign‑In)
     * -----------------------------------------------------------
     */

    @Operation(summary = "Admin sign-up")
    @PostMapping("/admins/sign-up")
    public ResponseEntity<Map<String, Object>> signUpAdmin(
            @RequestBody @Valid SignUpAdminResource body) {
        var created = commandService.signUpAdmin(body);
        var resource = UserAccountResourceFromEntityAssembler.toResource(created);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin registered successfully");
        response.put("user", resource);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Admin sign-in")
    @PostMapping("/admins/sign-in")
    public ResponseEntity<Map<String, Object>> signInAdmin(
            @RequestBody @Valid SignInAdminResource body) {
        var user = commandService.signInAdmin(body);
        var resource = UserAccountResourceFromEntityAssembler.toResource(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin login successful");
        response.put("user", resource);

        return ResponseEntity.ok(response);
    }

    /* -----------------------------------------------------------
     * 3) ADMIN ‑ PERFIL (Get / Update)
     * -----------------------------------------------------------
     */

    @Operation(summary = "Get admin profiles")
    @GetMapping("/admins")
    public ResponseEntity<List<UserAccountResource>> getAdmins() {
        var data = queryService.findAdmins()
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Edit admin profile")
    @PutMapping("/admins/{id}")
    public ResponseEntity<?> updateAdmin(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAdminResource resource) {

        var user = queryService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        if (!user.get().getRole().equals(Roles.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Only admins can be updated here"));
        }

        var updated = commandService.updateAdmin(id, resource);
        var resourceResult = UserAccountResourceFromEntityAssembler.toResource(updated);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Admin updated successfully",
                        "user", resourceResult
                )
        );
    }

    /* -----------------------------------------------------------
     * 4) TEACHER ‑ AUTH (Sign‑In)
     * -----------------------------------------------------------
     */

    @Operation(summary = "Teacher sign‑in")
    @PostMapping("/teachers/sign-in")
    public ResponseEntity<Map<String, Object>>  signInTeacher(
            @RequestBody @Valid SignInTeacherResource body) {
        var user = commandService.signInTeacher(body);
        var resource = UserAccountResourceFromEntityAssembler.toResource(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Teacher login successful");
        response.put("user", resource);

        return ResponseEntity.ok(response);
    }

    /* -----------------------------------------------------------
     * 5) TEACHER ‑ PERFIL (Get / Update / Delete / Create)
     * -----------------------------------------------------------
     */

    @Operation(summary = "Get teacher profiles")
    @GetMapping("/teachers")
    public ResponseEntity<List<UserAccountResource>> getTeachers() {
        var data = queryService.findTeachers()
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Add new teacher profile")
    @PostMapping("/teachers")
    public ResponseEntity<UserAccountResource> createTeacher(
            @RequestBody @Valid CreateTeacherResource resource) {
        var created = commandService.createTeacher(resource);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserAccountResourceFromEntityAssembler.toResource(created));
    }


    @Operation(summary = "Edit teacher profile")
    @PutMapping("/teachers/{id}")
    public ResponseEntity<?> updateTeacher(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTeacherResource resource) {

        var user = queryService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        if (!user.get().getRole().equals(Roles.TEACHER)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Only teachers can be updated here"));
        }

        var updated = commandService.updateTeacher(id, resource);
        var resourceResult = UserAccountResourceFromEntityAssembler.toResource(updated);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Teacher updated successfully",
                        "user", resourceResult
                )
        );
    }

    @Operation(summary = "Delete teacher")
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {

        var user = queryService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        if (!user.get().getRole().equals(Roles.TEACHER)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Only teachers can be deleted here"));
        }

        commandService.deleteTeacher(id);

        return ResponseEntity.ok(Map.of("message", "Teacher deleted successfully"));
    }



    /* -----------------------------------------------------------
     * 6) PASSWORD
     * -----------------------------------------------------------
     */

    @Operation(summary = "Reset password")
    @PutMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordResource resource) {
        commandService.resetPassword(resource);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Password reset successfully");
        response.put("email", resource.email());

        return ResponseEntity.ok(response);
    }
}
