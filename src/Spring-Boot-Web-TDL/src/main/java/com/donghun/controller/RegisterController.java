package com.donghun.controller;

import com.donghun.domain.User;
import com.donghun.repository.UserRepository;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register() {
        return "todolist/register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String currentName = user.getName();
        User  user1 = userService.findUserName(currentName);

        if(user1 != null)
            return new ResponseEntity<>("아이디가 존재합니다.", HttpStatus.FORBIDDEN);
        else {
            userRepository.save(user);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
    }
}
