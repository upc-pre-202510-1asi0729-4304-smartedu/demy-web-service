package com.smartedu.demy.platform.iam.interfaces.rest.controllers;

import com.smartedu.demy.platform.iam.application.internal.queryservices.AcademyQueryServiceImpl;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.AcademyResource;
import com.smartedu.demy.platform.iam.interfaces.rest.transform.AcademyResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/academies", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Academy", description = "Academy management endpoints")
public class AcademyController {

    private final AcademyQueryServiceImpl queryService;

    public AcademyController(AcademyQueryServiceImpl queryService) {
        this.queryService = queryService;
    }

    @Operation(summary = "Get all academies", description = "Retrieve a list of all registered academies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of academies retrieved",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "[{\"id\": \"string\", \"name\": \"string\", \"ruc\": \"string\"}]")))
    })
    @GetMapping
    public ResponseEntity<List<AcademyResource>> getAllAcademies() {
        var academies = queryService.getAllAcademies()
                .stream()
                .map(AcademyResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(academies);
    }
}
