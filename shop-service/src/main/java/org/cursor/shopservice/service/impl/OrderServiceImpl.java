package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.OrderDto;
import org.cursor.data.model.Order;
import org.cursor.shopservice.repository.OrderRepository;
import org.cursor.shopservice.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
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
    public Order createOrder(OrderDto orderDto) {
        return orderRepository.save(mapper.convertValue(orderDto, Order.class));
    }

    @Override
    public void updateOrder(UUID id, OrderDto orderDto) {
        orderRepository.findById(id).orElseThrow();
        var order = mapper.convertValue(orderDto, Order.class);
        order.setId(id);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.findById(id).orElseThrow();
        orderRepository.deleteById(id);
    }
}
