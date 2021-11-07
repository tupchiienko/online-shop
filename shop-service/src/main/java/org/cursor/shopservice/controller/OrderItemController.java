package org.cursor.shopservice.controller;

import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.OrderItemDto;
import org.cursor.data.model.OrderItem;
import org.cursor.shopservice.service.OrderItemService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orderItems")
@RequiredArgsConstructor
public class OrderItemController {

    private OrderItemService orderItemService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItemDto));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable UUID id, @RequestBody OrderItemDto orderItemDto) {
        orderItemService.updateOrderItem(id, orderItemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OrderItem> deleteOrderItem(@PathVariable UUID id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok().build();
    }
}
