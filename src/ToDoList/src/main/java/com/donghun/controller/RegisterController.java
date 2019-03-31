package com.donghun.controller;

import com.donghun.domain.UserDTO;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register() {
        return "todolist/register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            userService.validation(bindingResult);
        }

        String inputId = userDTO.getId();
        String inputEmail = userDTO.getEmail();

        if(userService.findUserId(inputId) != null)
            return new ResponseEntity<>("이미 존재하는 아이디입니다.", HttpStatus.FORBIDDEN);
        else if(userService.findUserEmail(inputEmail) != null)
            return new ResponseEntity<>("이미 존재하는 이메일입니다.", HttpStatus.FORBIDDEN);

        userService.DTOsave(userDTO);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
}
