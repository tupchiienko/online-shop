package org.cursor.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class OrderItemDto {

    private String name;
    private int quantity;
    private BigInteger price;

}
