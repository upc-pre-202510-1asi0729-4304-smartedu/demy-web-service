package com.smartedu.demy.platform.billing.interfaces.rest.controllers;

import com.smartedu.demy.platform.billing.domain.model.queries.GetAllInvoicesByDniQuery;
import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByIdQuery;
import com.smartedu.demy.platform.billing.domain.services.InvoiceCommandService;
import com.smartedu.demy.platform.billing.domain.services.InvoiceQueryService;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.CreateInvoiceResource;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.InvoiceResource;
import com.smartedu.demy.platform.billing.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import com.smartedu.demy.platform.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/invoices", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Endpoints for invoices")
public class InvoicesController {
    private final InvoiceQueryService invoiceQueryService;
    private final InvoiceCommandService invoiceCommandService;

    public InvoicesController(InvoiceQueryService invoiceQueryService, InvoiceCommandService invoiceCommandService) {
        this.invoiceQueryService = invoiceQueryService;
        this.invoiceCommandService = invoiceCommandService;
    }

    @GetMapping("/by-student/{dni}")
    public ResponseEntity<List<InvoiceResource>> getAllInvoicesByDni(@PathVariable String dni) {
        var getAllInvoicesByDniQuery = new GetAllInvoicesByDniQuery(new Dni(dni));
        var invoiceResources = invoiceQueryService.handle(getAllInvoicesByDniQuery).stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(invoiceResources);
    }

    @PostMapping
    public ResponseEntity<InvoiceResource> createInvoice(@RequestBody CreateInvoiceResource resource) {
        var createInvoiceCommand = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var invoiceId = invoiceCommandService.handle(createInvoiceCommand);
        var getInvoiceByIdQuery = new GetInvoiceByIdQuery(invoiceId);
        var invoice = invoiceQueryService.handle(getInvoiceByIdQuery);
        if (invoice.isEmpty()) return ResponseEntity.notFound().build();
        var invoiceEntity = invoice.get();
        var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(invoiceEntity);
        return new ResponseEntity<>(invoiceResource, HttpStatus.CREATED);
    }
}
