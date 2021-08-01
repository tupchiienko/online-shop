package org.cursor.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cursor.data.enums.Role;

@Setter
@Getter
@ToString
public class AccountDto {

    private String username;
    private String email;
    private String password;
    private Role role;

}
