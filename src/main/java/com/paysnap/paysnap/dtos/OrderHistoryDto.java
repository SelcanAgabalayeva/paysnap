package com.paysnap.paysnap.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDto {
    private Long orderId;
    private Double amount;
    private String currency;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private String receiptUrl;
}
