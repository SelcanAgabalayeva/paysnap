package com.paysnap.paysnap.controllers;

import com.paysnap.paysnap.entity.Order;
import com.paysnap.paysnap.repositories.OrderRepository;
import com.paysnap.paysnap.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ReceiptController {

    private final ReceiptService service;
    private final OrderRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> receipt(@PathVariable Long id) {

        Order order = repo.findById(id).orElseThrow();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(service.generate(order));
    }
}