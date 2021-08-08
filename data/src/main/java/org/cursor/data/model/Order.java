package org.cursor.data.model;

import lombok.*;
import org.cursor.data.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    private Date date;

    @Column(name = "order_number")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long orderNumber;

    @Column(name = "order_price")
    private BigInteger orderPrice;

    @Column(name = "order_status")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinTable(
            name = "orders_items",
            joinColumns = @JoinColumn(
                    name = "order_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "item_id"
            )
    )
    @ToString.Exclude
    private List<OrderItem> orderItems;
}
