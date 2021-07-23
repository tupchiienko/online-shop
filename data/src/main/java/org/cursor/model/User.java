package org.cursor.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "users_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String phone;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account accountId;



}
