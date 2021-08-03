package org.cursor.authservice.service;

import org.cursor.data.dto.AccountDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    void register(AccountDto accountDto);

}
