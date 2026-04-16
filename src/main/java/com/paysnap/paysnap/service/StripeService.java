package com.paysnap.paysnap.service;

import com.paysnap.paysnap.entity.Order;

public interface StripeService {
    String createSession(Order order) throws Exception;
    void handleWebhook(String payload, String signature, String webhookSecret) throws Exception;
}
