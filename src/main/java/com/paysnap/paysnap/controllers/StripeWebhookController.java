package com.paysnap.paysnap.controllers;

import com.paysnap.paysnap.service.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/stripe")
public class StripeWebhookController {

    private final StripeService stripeService;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public StripeWebhookController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping
    public ResponseEntity<String> handle(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {
        try {
            stripeService.handleWebhook(payload, signature, webhookSecret);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("webhook error");
        }
    }
}