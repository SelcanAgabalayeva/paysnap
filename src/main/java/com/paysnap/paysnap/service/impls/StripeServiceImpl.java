package com.paysnap.paysnap.service.impls;


import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.enums.OrderStatus;
import com.paysnap.paysnap.repositories.OrderRepository;
import com.paysnap.paysnap.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StripeServiceImpl implements StripeService {
    private final OrderRepository orderRepository;

    @Value("${stripe.secret}")
    private String key;

    public StripeServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public String createSession(Order order) throws Exception {

        Stripe.apiKey = key;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(order.getCurrency())
                                                .setUnitAmount((long)(order.getAmount() * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(order.getDescription())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        // session id DB-yə yazmaq çox vacibdir
        order.setStripeSessionId(session.getId());
        order.setPaymentUrl(session.getUrl());
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);


        return session.getUrl();
    }

    @Override
    public void handleWebhook(String payload, String signature, String webhookSecret) throws Exception {

        Event event = Webhook.constructEvent(
                payload,
                signature,
                webhookSecret
        );

        switch (event.getType()) {

            case "checkout.session.completed":
                handleSuccess(event);
                break;

            case "checkout.session.expired":
                handleFailed(event);
                break;

            default:
                break;
        }
    }

    private void handleSuccess(Event event) {

        Session session = (Session) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow();

        String sessionId = session.getId();

        Order order = orderRepository.findByStripeSessionId(sessionId)
                .orElseThrow();

        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    private void handleFailed(Event event) {

        Session session = (Session) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow();

        String sessionId = session.getId();

        Order order = orderRepository.findByStripeSessionId(sessionId)
                .orElseThrow();

        order.setStatus(OrderStatus.FAILED);

        orderRepository.save(order);
    }
}