package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.OrderItemDto;
import org.cursor.data.model.OrderItem;
import org.cursor.shopservice.repository.OrderItemRepository;
import org.cursor.shopservice.service.OrderItemService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    private ObjectMapper mapper;

    @Override
    public OrderItem getOrderItemById(UUID id) {
        return orderItemRepository.findById(id).orElseThrow();
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public OrderItem createOrderItem(OrderItemDto orderItemDto) {
        return orderItemRepository.save(mapper.convertValue(orderItemDto, OrderItem.class));
    }

    @Override
    public void updateOrderItem(UUID id, OrderItemDto orderItemDto) {
        orderItemRepository.findById(id).orElseThrow();
        var orderItem = mapper.convertValue(orderItemDto, OrderItem.class);
        orderItem.setId(id);
        orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(UUID id) {
        orderItemRepository.findById(id).orElseThrow();
        orderItemRepository.deleteById(id);
    }
}
