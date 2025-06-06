package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.cursor.data.dto.OrderDto;
import org.cursor.data.enums.OrderStatus;
import org.cursor.data.model.Order;
import org.cursor.data.model.OrderItem;
import org.cursor.shopservice.repository.OrderRepository;
import org.cursor.shopservice.service.OrderItemService;
import org.cursor.shopservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemService orderItemService;

    private OrderServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OrderServiceImpl();
        ReflectionTestUtils.setField(service, "orderRepository", orderRepository);
        ReflectionTestUtils.setField(service, "orderItemService", orderItemService);
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ReflectionTestUtils.setField(service, "mapper", mapper);
    }

    @Test
    void getOrderById_returnsOrder() {
        UUID id = UUID.randomUUID();
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order result = service.getOrderById(id);

        assertThat(result).isSameAs(order);
    }

    @Test
    void getAllOrders_returnsList() {
        List<Order> list = List.of(new Order());
        when(orderRepository.findAll(any(Sort.class))).thenReturn(list);

        List<Order> result = service.getAllOrders();

        assertThat(result).isEqualTo(list);
    }

    @Test
    void getAllOrdersByUserId_delegatesToRepo() {
        UUID userId = UUID.randomUUID();
        List<Order> list = List.of(new Order());
        when(orderRepository.findAllByUserId(userId)).thenReturn(list);

        List<Order> result = service.getAllOrdersByUserId(userId);

        assertThat(result).isEqualTo(list);
    }

    @Test
    void createOrder_setsStatusAndSaves() {
        OrderDto dto = new OrderDto();
        dto.setOrderItemsIds(List.of(UUID.randomUUID()));
        OrderItem item = new OrderItem();
        when(orderItemService.getAllByIds(dto.getOrderItemsIds())).thenReturn(List.of(item));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        Order result = service.createOrder(dto);

        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(result.getOrderItems()).containsExactly(item);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_updatesExisting() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.of(new Order()));
        OrderDto dto = new OrderDto();
        dto.setOrderItemsIds(List.of(UUID.randomUUID()));
        when(orderItemService.getAllByIds(dto.getOrderItemsIds())).thenReturn(List.of(new OrderItem()));

        service.updateOrder(id, dto);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void deleteOrder_deletesExisting() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.of(new Order()));

        service.deleteOrder(id);

        verify(orderRepository).deleteById(id);
    }
}
