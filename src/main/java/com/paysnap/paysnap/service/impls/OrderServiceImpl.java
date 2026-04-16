package com.paysnap.paysnap.service.impls;

import com.paysnap.paysnap.dtos.OrderCreateDto;
import com.paysnap.paysnap.dtos.OrderDto;
import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.enums.OrderStatus;
import com.paysnap.paysnap.entity.User;
import com.paysnap.paysnap.repositories.OrderRepository;
import com.paysnap.paysnap.repositories.UserRepository;
import com.paysnap.paysnap.service.OrderService;
import com.paysnap.paysnap.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StripeService stripeService;

    @Override
    public OrderDto create(OrderCreateDto dto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setAmount(dto.getAmount());
        order.setCurrency(dto.getCurrency());
        order.setDescription(dto.getDescription());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);

        try {
            String url = stripeService.createSession(order);
            order.setPaymentUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }

        Order saved = orderRepository.save(order);

        // MANUAL DTO MAP HERE
        OrderDto res = new OrderDto();
        res.setId(saved.getId());
        res.setAmount(saved.getAmount());
        res.setCurrency(saved.getCurrency());
        res.setDescription(saved.getDescription());
        res.setStatus(saved.getStatus().name());
        res.setPaymentUrl(saved.getPaymentUrl());
        res.setCreatedAt(saved.getCreatedAt());

        return res;
    }

    @Override
    public List<OrderDto> getAll(String email) {
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(o -> {
                    OrderDto dto = new OrderDto();
                    dto.setId(o.getId());
                    dto.setAmount(o.getAmount());
                    dto.setCurrency(o.getCurrency());
                    dto.setDescription(o.getDescription());
                    dto.setStatus(o.getStatus().name());
                    dto.setPaymentUrl(o.getPaymentUrl());
                    dto.setCreatedAt(o.getCreatedAt());
                    return dto;
                })
                .toList();
    }

    @Override
    public OrderDto getById(Long id, String email) {
        Order o = orderRepository.findById(id)
                .orElseThrow();

        if (!o.getUser().getEmail().equals(email))
            throw new RuntimeException("Forbidden");

        OrderDto dto = new OrderDto();
        dto.setId(o.getId());
        dto.setAmount(o.getAmount());
        dto.setCurrency(o.getCurrency());
        dto.setDescription(o.getDescription());
        dto.setStatus(o.getStatus().name());
        dto.setPaymentUrl(o.getPaymentUrl());
        dto.setCreatedAt(o.getCreatedAt());

        return dto;
    }
}
