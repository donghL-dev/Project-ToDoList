package com.donghun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dongh9508
 * @since 2019-07-08
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping
    public String accountPage() {
        return "account/account";
    }
}
