package com.donghun.controller;

import com.donghun.domain.UserDTO;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author dongh9508
 * @since 2019-03-29
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder msg = userService.validation(bindingResult);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        String inputId = userDTO.getId();
        String inputEmail = userDTO.getEmail();

        if (userService.findUserId(inputId) != null)
            return new ResponseEntity<>("이미 존재하는 아이디입니다.", HttpStatus.FORBIDDEN);
        else if (userService.findUserEmail(inputEmail) != null)
            return new ResponseEntity<>("이미 존재하는 이메일입니다.", HttpStatus.FORBIDDEN);

        userService.DTOsave(userDTO);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/register/idcheck")
    public ResponseEntity<?> idCheck(@RequestBody String id) {
        return userService.findUserId(id) != null ? new ResponseEntity<>("이미 사용중인 아이디입니다.", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("사용가능한 멋진 아이디입니다.", HttpStatus.OK);
    }

    @PostMapping("/register/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody String email) {
        return userService.findUserEmail(email) != null ? new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("사용가능한 멋진 이메일입니다.", HttpStatus.OK);
    }


}
