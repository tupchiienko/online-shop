package org.cursor.data.model;

import lombok.*;
import org.cursor.data.enums.Availability;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "positions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position extends BaseEntity {

    private String name;
    private BigInteger price;
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Availability availability;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
