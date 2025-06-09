package com.smartedu.demy.platform.billing.interfaces.rest.controllers;

import com.smartedu.demy.platform.billing.domain.model.queries.GetInvoiceByStudentIdQuery;
import com.smartedu.demy.platform.billing.domain.services.InvoiceQueryService;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.InvoiceResource;
import com.smartedu.demy.platform.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/invoices", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Endpoints for invoices")
public class InvoiceController {
    private final InvoiceQueryService invoiceQueryService;

    public InvoiceController(InvoiceQueryService invoiceQueryService) {
        this.invoiceQueryService = invoiceQueryService;
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<InvoiceResource>> getInvoiceByStudentId(@PathVariable Long studentId) {
        var getInvoiceByStudentIdQuery = new GetInvoiceByStudentIdQuery(new StudentId(studentId));
        var invoiceResources = invoiceQueryService.handle(getInvoiceByStudentIdQuery).stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(invoiceResources);
    }
}
