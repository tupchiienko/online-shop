package org.cursor.data.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    private String name;
    private int quantity;
    private BigInteger price;

}
