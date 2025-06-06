import com.fasterxml.jackson.databind.ObjectMapper;
import org.cursor.data.dto.OrderItemDto;
import org.cursor.data.model.OrderItem;
import org.cursor.shopservice.repository.OrderItemRepository;
import org.cursor.shopservice.service.impl.OrderItemServiceImpl;
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
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository repository;

    private OrderItemServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OrderItemServiceImpl();
        ReflectionTestUtils.setField(service, "orderItemRepository", repository);
        ReflectionTestUtils.setField(service, "mapper", new ObjectMapper());
    }

    @Test
    void getOrderItemById_returnsItem() {
        UUID id = UUID.randomUUID();
        OrderItem item = new OrderItem();
        when(repository.findById(id)).thenReturn(Optional.of(item));

        OrderItem result = service.getOrderItemById(id);

        assertThat(result).isSameAs(item);
    }

    @Test
    void getAllOrderItems_returnsList() {
        List<OrderItem> list = List.of(new OrderItem());
        when(repository.findAll(any(Sort.class))).thenReturn(list);

        List<OrderItem> result = service.getAllOrderItems();

        assertThat(result).isEqualTo(list);
    }

    @Test
    void getAllByIds_delegatesToRepository() {
        List<UUID> ids = List.of(UUID.randomUUID());
        List<OrderItem> list = List.of(new OrderItem());
        when(repository.findAllById(ids)).thenReturn(list);

        List<OrderItem> result = service.getAllByIds(ids);

        assertThat(result).isEqualTo(list);
    }

    @Test
    void createOrderItem_savesMappedEntity() {
        OrderItemDto dto = new OrderItemDto();
        dto.setName("n");
        OrderItem saved = new OrderItem();
        when(repository.save(any(OrderItem.class))).thenReturn(saved);

        OrderItem result = service.createOrderItem(dto);

        assertThat(result).isSameAs(saved);
        ArgumentCaptor<OrderItem> captor = ArgumentCaptor.forClass(OrderItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("n");
    }

    @Test
    void updateOrderItem_updatesExisting() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.of(new OrderItem()));
        OrderItemDto dto = new OrderItemDto();
        dto.setName("u");

        service.updateOrderItem(id, dto);

        ArgumentCaptor<OrderItem> captor = ArgumentCaptor.forClass(OrderItem.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(id);
        assertThat(captor.getValue().getName()).isEqualTo("u");
    }

    @Test
    void deleteOrderItem_deletesExisting() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.of(new OrderItem()));

        service.deleteOrderItem(id);

        verify(repository).deleteById(id);
    }
}
