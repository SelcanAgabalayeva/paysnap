package com.paysnap.paysnap.service.impls;

import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.enums.OrderStatus;
import com.paysnap.paysnap.repositories.OrderRepository;
import com.paysnap.paysnap.service.EmailService;
import com.paysnap.paysnap.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final OrderRepository orderRepository;
    private final ReceiptService receiptService;
    private final EmailService emailService;

    public void handlePaymentSuccess(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        // 1. update status
        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        orderRepository.save(order);

        // 2. generate PDF
        byte[] pdf = receiptService.generate(order);

        // 3. send email
        emailService.sendReceiptEmail(
                order.getUser().getEmail(),
                order,
                pdf
        );
    }
}
