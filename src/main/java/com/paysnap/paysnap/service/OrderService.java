package com.paysnap.paysnap.service;

import com.paysnap.paysnap.dtos.OrderCreateDto;
import com.paysnap.paysnap.dtos.OrderDto;
import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.entity.User;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderCreateDto dto, String email);

    List<OrderDto> getAll(String email);

    OrderDto getById(Long id, String email);
}
