package org.cursor.data.model;

import lombok.*;
import org.cursor.data.enums.Availability;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

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
    private String article;

    @OneToMany
    @JoinTable(
            name = "positions_images",
            joinColumns = @JoinColumn(
                    name = "position_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "image_id"
            )
    )
    @ToString.Exclude
    private List<Image> images;

    @Enumerated(EnumType.ORDINAL)
    private Availability availability;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
