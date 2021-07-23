package org.cursor.model;

import lombok.Data;
import org.cursor.model.enums.Role;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "account_table")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private final String username;
    private final String email;
    private final String password;
    @Enumerated(EnumType.STRING)
    private final Role role;
}
