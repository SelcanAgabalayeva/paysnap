package com.paysnap.paysnap.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {

    private Long id;
    private String description;
    private Double amount;
    private String currency;

    private String paymentUrl;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime paidAt;
}
