package org.cursor.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.cursor.accountservice.service.AccountService;
import org.cursor.data.dto.AccountDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-service")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(
            value = "/auth/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto) {
        accountService.register(accountDto);
        return ResponseEntity.ok().build();
    }

}
