package org.cursor.accountservice.service;

import org.cursor.data.dto.AccountDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {

    void register(AccountDto accountDto);

}
