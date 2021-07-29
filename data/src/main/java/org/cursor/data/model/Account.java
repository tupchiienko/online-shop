package org.cursor.data.model;

import lombok.*;
import org.cursor.data.enums.Role;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "account_table")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Account extends BaseEntity {

    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
