package com.backend.demo.services.customer.cart;

import com.backend.demo.Entity.Order;
import com.backend.demo.dto.OrderDto;
import com.backend.demo.enums.OrderStatus;
import com.backend.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CancleOrderServiceImpl implements CancleOrderService {

    private final OrderRepository orderRepository;

    public OrderDto cancleOrder(Long orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (Objects.equals(status, "Cancelled")) {
                order.setOrderStatus(OrderStatus.Cancelled);
                return orderRepository.save(order).getOrderDto();
            }
        }
        return null;
    }
}