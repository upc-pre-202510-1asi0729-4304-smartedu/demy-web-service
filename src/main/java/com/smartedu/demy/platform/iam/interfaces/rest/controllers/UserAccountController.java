package com.smartedu.demy.platform.iam.interfaces.rest.controllers;

import com.smartedu.demy.platform.iam.application.internal.queryservices.UserAccountQueryServiceImpl;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UserAccountResource;
import com.smartedu.demy.platform.iam.interfaces.rest.transform.UserAccountResourceFromEntityAssembler;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountController {

    private final UserAccountQueryServiceImpl userAccountQueryService;

    public UserAccountController(UserAccountQueryServiceImpl userAccountQueryService) {
        this.userAccountQueryService = userAccountQueryService;
    }

    @GetMapping("/{id}")
    public UserAccountResource getUser(@PathVariable Long id) {
        return userAccountQueryService.findById(id)
                .map(UserAccountResourceFromEntityAssembler::toResource)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
