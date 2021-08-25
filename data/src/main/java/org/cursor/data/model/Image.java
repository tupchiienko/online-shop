package org.cursor.data.model;

import lombok.*;
import org.cursor.data.enums.ImageFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "images")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private ImageFormat format;

}
