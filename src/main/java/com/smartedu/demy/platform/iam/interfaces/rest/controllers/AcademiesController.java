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


/**
 * REST controller for managing academy-related endpoints.
 * Provides access to operations for retrieving academy data via HTTP requests.
 */
@RestController
@RequestMapping(value = "/api/v1/academies", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Academy", description = "Academy management endpoints")
public class AcademiesController {

    private final AcademyQueryServiceImpl queryService;

    /**
     * Constructs a new {@code AcademiesController} with the required query service.
     *
     * @param queryService the service used to query academy data
     */
    public AcademiesController(AcademyQueryServiceImpl queryService) {
        this.queryService = queryService;
    }

    /**
     * Retrieves a list of all registered academies.
     *
     * @return a {@link ResponseEntity} containing a list of {@link AcademyResource} objects
     */
    @Operation(summary = "Get all academies", description = "Retrieve a list of all registered academies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of academies retrieved",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "[{\"id\": \"string\", \"name\": \"string\", \"ruc\": \"string\"}]")))
    })
    @GetMapping
    public ResponseEntity<List<AcademyResource>> getAllAcademies() {

        /**
         * Retrieves all academies from the database using the query service,
         * transforms each academy entity into a resource DTO, and returns them in the response.
         */

        var academies = queryService.getAllAcademies()
                .stream()
                .map(AcademyResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        /**
         * Returns the list of academy resources wrapped in an HTTP 200 OK response.
         */
        return ResponseEntity.ok(academies);
    }
}
