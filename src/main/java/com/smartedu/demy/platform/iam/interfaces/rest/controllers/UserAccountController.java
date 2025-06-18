package com.smartedu.demy.platform.iam.interfaces.rest.controllers;

import com.smartedu.demy.platform.iam.domain.services.UserAccountQueryService;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.*;
import com.smartedu.demy.platform.iam.interfaces.rest.transform.UserAccountResourceFromEntityAssembler;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @Operation(summary = "Get admin profiles")
    @GetMapping("/admins")
    public ResponseEntity<List<UserAccountResource>> getAdmins() {
        var data = queryService.findAdmins()
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get teacher profiles")
    @GetMapping("/teachers")
    public ResponseEntity<List<UserAccountResource>> getTeachers() {
        var data = queryService.findTeachers()
                .stream()
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountResource> getById(@PathVariable Long id) {
        return queryService.findById(id)
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<UserAccountResource> updateTeacher(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTeacherResource resource) {
        var updated = commandService.updateTeacher(id, resource);
        return ResponseEntity.ok(UserAccountResourceFromEntityAssembler.toResource(updated));
    }

    @Operation(summary = "Edit admin profile")
    @PutMapping("/admins/{id}")
    public ResponseEntity<UserAccountResource> updateAdmin(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAdminResource resource) {
        var updated = commandService.updateAdmin(id, resource);
        return ResponseEntity.ok(UserAccountResourceFromEntityAssembler.toResource(updated));
    }

    @Operation(summary = "Delete teacher")
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        commandService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordResource resource) {
        commandService.resetPassword(resource);
        return ResponseEntity.ok("Password reset successfully");
    }
}
