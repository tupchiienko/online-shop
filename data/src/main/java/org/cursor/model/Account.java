package org.cursor.model;

import lombok.Data;
import org.cursor.model.enums.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Table(name = "account_table")
@Data
public class Account {

    private final String username;
    private final String email;
    private final String password;
    @Enumerated(EnumType.STRING)
    private final Role role;
}
