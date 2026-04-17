package com.paysnap.paysnap.controllers;

import com.paysnap.paysnap.dtos.OrderCreateDto;
import com.paysnap.paysnap.dtos.OrderDto;
import com.paysnap.paysnap.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public OrderDto create(@Valid @RequestBody OrderCreateDto dto,
                                   Principal principal) {
        return service.create(dto, principal.getName());
    }

    @GetMapping
    public List<OrderDto> getAll(Principal principal) {
        return service.getAll(principal.getName());
    }

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id, Principal p) {
        return service.getById(id, p.getName());
    }
}
