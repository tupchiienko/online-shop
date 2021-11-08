package org.cursor.shopservice.service;

import org.cursor.data.dto.OrderDto;
import org.cursor.data.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order getOrderById(UUID id);

    List<Order> getAllOrders();

    List<Order> getAllOrdersByUserId(UUID userId);

    Order createOrder(OrderDto orderDto);

    void updateOrder(UUID id, OrderDto orderDto);

    void deleteOrder(UUID id);

}
