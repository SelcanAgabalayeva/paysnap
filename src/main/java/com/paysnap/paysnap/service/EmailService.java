package com.paysnap.paysnap.service;

import com.paysnap.paysnap.entity.Order;

public interface EmailService {
    void sendReceiptEmail(String toEmail, Order order, byte[] pdf);
}
