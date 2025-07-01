package com.smartedu.demy.platform.billing.interfaces.rest.controllers;

import com.smartedu.demy.platform.billing.domain.services.FinancialTransactionCommandService;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.CreateFinancialTransactionResource;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.FinancialTransactionResource;
import com.smartedu.demy.platform.billing.interfaces.rest.resources.RegisterPaymentResource;
import com.smartedu.demy.platform.billing.interfaces.rest.transform.CreateFinancialTransactionCommandFromResourceAssembler;
import com.smartedu.demy.platform.billing.interfaces.rest.transform.FinancialTransactionResourceFromEntityAssembler;
import com.smartedu.demy.platform.billing.interfaces.rest.transform.RegisterPaymentCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/financial-transactions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Financial Transactions", description = "Endpoints for financial transactions")
public class FinancialTransactionsController {
    private final FinancialTransactionCommandService financialTransactionCommandService;

    public FinancialTransactionsController(FinancialTransactionCommandService financialTransactionCommandService) {
        this.financialTransactionCommandService = financialTransactionCommandService;
    }

    @PostMapping
    public ResponseEntity<FinancialTransactionResource> createFinancialTransaction(
            @RequestBody CreateFinancialTransactionResource resource) {
        var createFinancialTransactionCommand = CreateFinancialTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        var financialTransaction = financialTransactionCommandService.handle(createFinancialTransactionCommand);
        if (financialTransaction.isEmpty()) return ResponseEntity.badRequest().build();
        var createdFinancialTransaction = financialTransaction.get();
        var financialTransactionResource = FinancialTransactionResourceFromEntityAssembler.toResourceFromEntity(createdFinancialTransaction);
        return new ResponseEntity<>(financialTransactionResource, HttpStatus.CREATED);
    }

    @PostMapping("/invoices/{invoiceId}/payment")
    public ResponseEntity<FinancialTransactionResource> registerPayment(
            @PathVariable Long invoiceId, @RequestBody RegisterPaymentResource resource
    ) {
        var registerPaymentCommand = RegisterPaymentCommandFromResourceAssembler.toCommandFromResource(invoiceId, resource);
        var financialTransaction = financialTransactionCommandService.handle(registerPaymentCommand);
        if (financialTransaction.isEmpty()) return ResponseEntity.badRequest().build();
        var createdFinancialTransaction = financialTransaction.get();
        var financialTransactionResource = FinancialTransactionResourceFromEntityAssembler.toResourceFromEntity(createdFinancialTransaction);
        return new ResponseEntity<>(financialTransactionResource, HttpStatus.CREATED);
    }
}
