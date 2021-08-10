package org.cursor.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cursor.data.enums.Availability;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@ToString
public class PositionDto {

    private String name;
    private BigInteger price;
    private String description;
    private String article;
    private Availability availability;
    private UUID categoryId;

}
