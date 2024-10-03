package com.backend.demo.services.admin.adminOrder;

import com.backend.demo.Entity.Order;
import com.backend.demo.dto.OrderDto;
import com.backend.demo.enums.OrderStatus;
import com.backend.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlaceOrders(){
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(
                List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered,OrderStatus.Cancelled)
        );

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if ("Shipped".equalsIgnoreCase(status)) {
                order.setOrderStatus(OrderStatus.Shipped);
            } else if ("Delivered".equalsIgnoreCase(status)) {
                order.setOrderStatus(OrderStatus.Delivered);
            } else {
                return null; // Or throw an appropriate exception if status is invalid
            }

            return orderRepository.save(order).getOrderDto();
        }
        return null; // Return null if the order is not found
    }


}
