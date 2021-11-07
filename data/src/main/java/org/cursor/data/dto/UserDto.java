package org.cursor.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDto {

    private String firstName;
    private String lastName;
    private int age;
    private String phone;
    private UUID accountId;

}
