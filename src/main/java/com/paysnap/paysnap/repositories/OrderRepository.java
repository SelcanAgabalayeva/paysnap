package com.paysnap.paysnap.repositories;

import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserEmail(String email);

    Optional<Order> findByStripeSessionId(String sessionId);
}
