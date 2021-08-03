package org.cursor.authservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.authservice.repository.AccountRepository;
import org.cursor.data.dto.AccountDto;
import org.cursor.data.exception.AccountAlreadyExistException;
import org.cursor.data.model.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public void register(AccountDto accountDto) {
        var newAccount = mapper.convertValue(accountDto, Account.class);
        newAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        if (accountRepository.findByUsername(newAccount.getUsername()).isPresent()) {
            throw new AccountAlreadyExistException("Username '" + newAccount.getUsername() + "' already exists");
        }
        accountRepository.save(newAccount);
    }
}
