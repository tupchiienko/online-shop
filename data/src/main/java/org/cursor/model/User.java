package org.cursor.model;

import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "users_table")
@Data
public class User {

    private final String firstName;
    private final String lastName;
    private final int age;
    private final String phone;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account accountId;


}
