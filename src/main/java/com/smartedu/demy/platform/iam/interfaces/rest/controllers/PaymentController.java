package com.smartedu.demy.platform.iam.interfaces.rest.controllers;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for handling Stripe payment operations.
 */
@RestController
@RequestMapping(value = "/api/v1/payments", produces = APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Tag(name = "Payments", description = "Stripe payment processing endpoints")
public class PaymentController {

    @Value("${stripe.secret-key}")
    private String stripeKey;

    /**
     * Creates a new PaymentIntent using the Stripe API and returns the client secret.
     * <p>
     * The request body must contain at least the amount. Optionally, it can include
     * the customer's name, email, payment description, cardholder name, and country.
     * All these values (except amount) will be stored as metadata in the PaymentIntent for
     * later tracking or reporting.
     *
     * @param data A map representing the request body with the following keys:
     *             <ul>
     *                 <li><b>amount</b> (required): The payment amount in cents (e.g., 500 = $5.00)</li>
     *                 <li><b>name</b> (optional): Customer name; default is "Guest"</li>
     *                 <li><b>email</b> (optional): Customer email; default is "guest@example.com"</li>
     *                 <li><b>description</b> (optional): Description of the payment; default is "No description"</li>
     *                 <li><b>cardholderName</b> (optional): Name of the cardholder; default is "Desconocido"</li>
     *                 <li><b>country</b> (optional): Country code (e.g., "PE"); default is "PE"</li>
     *             </ul>
     * @return A map containing the client secret to complete the payment on the frontend.
     * @throws Exception if an error occurs during the creation of the customer or payment intent.
     */
    @PostMapping("/create-intent")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) throws Exception {
        Stripe.apiKey = stripeKey;

        long amountInCents = Long.parseLong(data.get("amount").toString());
        String name = data.containsKey("name") ? data.get("name").toString() : "Guest";
        String email = data.containsKey("email") ? data.get("email").toString() : "guest@example.com";
        String description = data.containsKey("description") ? data.get("description").toString() : "No description";
        String cardholderName = data.containsKey("cardholderName") ? data.get("cardholderName").toString() : "Desconocido";
        String address = data.containsKey("address") ? data.get("address").toString() : "No address";

        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setName(name)
                .setEmail(email)
                .build();
        Customer customer = Customer.create(customerParams);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setCustomer(customer.getId())
                .setDescription(description)
                .putMetadata("cardholderName", cardholderName)
                .putMetadata("clientName", name)
                .putMetadata("clientEmail", email)
                .putMetadata("address", address)
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        return response;
    }
}