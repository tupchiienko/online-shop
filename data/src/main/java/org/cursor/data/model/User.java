package org.cursor.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users_table")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private int age;
    private String phone;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account accountId;

}
