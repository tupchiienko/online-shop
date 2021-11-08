package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.OrderDto;
import org.cursor.data.enums.OrderStatus;
import org.cursor.data.model.Order;
import org.cursor.shopservice.repository.OrderRepository;
import org.cursor.shopservice.service.OrderItemService;
import org.cursor.shopservice.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemService orderItemService;
    private ObjectMapper mapper;

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "data"));
    }

    @Override
    public List<Order> getAllOrdersByUserId(UUID userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        Order order = mapper.convertValue(orderDto, Order.class);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setOrderItems(orderItemService.getAllByIds(orderDto.getOrderItemsIds()));
        return orderRepository.save(order);
    }

    @Override
    public void updateOrder(UUID id, OrderDto orderDto) {
        orderRepository.findById(id).orElseThrow();
        var order = mapper.convertValue(orderDto, Order.class);
        order.setId(id);
        order.setOrderItems(orderItemService.getAllByIds(orderDto.getOrderItemsIds()));
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.findById(id).orElseThrow();
        orderRepository.deleteById(id);
    }
}
