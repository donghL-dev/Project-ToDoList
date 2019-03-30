package com.donghun.controller;

import com.donghun.domain.Account;
import com.donghun.repository.AccountRepository;
import com.donghun.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/create")
    public Account create() {
        Account account = new Account();
        account.setEmail("user@gmail.com");
        account.setPassword("1234");

        return accountService.save(account);
    }
}
