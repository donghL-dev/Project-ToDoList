package com.donghun.controller;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.repository.UserRepository;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "todolist/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> map) {
        String currentName = map.get("name");
        String currentPassword = map.get("password");

        User user1 = userService.findUserName(currentName);

        if(user1 != null && user1.getPassword().equals(currentPassword))
            return new ResponseEntity<>("{}", HttpStatus.OK);
        else if(user1 != null && !user1.getPassword().equals(currentPassword))
            return new ResponseEntity<>("비밀번호가 옳지 않습니다.", HttpStatus.FORBIDDEN);
        else
            return new ResponseEntity<>("아이디가 옳지 않습니다", HttpStatus.FORBIDDEN);

    }

}
