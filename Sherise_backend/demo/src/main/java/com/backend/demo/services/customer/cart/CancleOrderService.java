package com.backend.demo.services.customer.cart;

import com.backend.demo.dto.OrderDto;

public interface CancleOrderService {

    OrderDto cancleOrder(Long orderId, String status);
}