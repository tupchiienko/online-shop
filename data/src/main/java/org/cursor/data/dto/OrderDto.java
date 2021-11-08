package org.cursor.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cursor.data.enums.OrderStatus;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class OrderDto {

    private Date date;
    private long orderNumber;
    private BigInteger orderPrice;
    private OrderStatus orderStatus;
    private UUID userId;
    private List<UUID> orderItemsIds;
}
