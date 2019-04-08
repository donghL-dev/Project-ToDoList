package com.donghun.controller;

import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Controller
public class LoginController {

    @GetMapping("/")
    public String root() {
        return "redirect:/todolist";
    }

    @GetMapping("/login")
    public String login() {
        return "todolist/login";
    }

}
