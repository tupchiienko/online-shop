package org.cursor.shopservice.service;

import org.cursor.data.dto.OrderItemDto;
import org.cursor.data.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {

    OrderItem getOrderItemById(UUID id);

    List<OrderItem> getAllOrderItems();

    List<OrderItem> getAllByIds(List<UUID> idList);

    OrderItem createOrderItem(OrderItemDto orderItemDto);

    void updateOrderItem(UUID id, OrderItemDto orderItemDto);

    void deleteOrderItem(UUID id);
}
