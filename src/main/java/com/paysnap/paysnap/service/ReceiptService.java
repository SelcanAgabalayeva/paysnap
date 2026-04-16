package com.paysnap.paysnap.service;

import com.paysnap.paysnap.entity.Order;


public interface ReceiptService {
    byte[] generate(Order order);
}
