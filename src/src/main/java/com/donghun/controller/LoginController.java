package com.donghun.controller;

import com.donghun.domain.UserDTO;
import com.donghun.service.LoginService;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @GetMapping("/")
    public String root() {
        return "redirect:/todolist";
    }

    @GetMapping("/login")
    public String login() {
        return "todolist/login";
    }

    @PostMapping("/login/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody String email) {
        return userService.findUserEmail(email) != null ? new ResponseEntity<>("{}", HttpStatus.OK)
                : new ResponseEntity<>("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/idcheck")
    public ResponseEntity<?> idCheck(@RequestBody String id) {
        return userService.findUserId(id) != null ? new ResponseEntity<>("{}", HttpStatus.OK)
                : new ResponseEntity<>("존재하지 않는 아이디입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/emailsendId")
    public ResponseEntity<?> emailSendId(@RequestBody String email) {
        String username = userService.findUserEmail(email).getId();
        loginService.sendMailId(email, username);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/login/emailsendPW")
    public ResponseEntity<?> emailSendPW(@RequestBody UserDTO userDTO) {
        String username = userService.findUserEmail(userDTO.getEmail()).getId();

        if(!username.equals(userDTO.getId()))
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);

        loginService.sendMailPW(userDTO.getEmail(), username);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/login/cnumberVaild")
    public ResponseEntity<?> cnumberVaild(@RequestBody Map<String, String> map) {
        String username = map.get("id");
        Integer cnumber = Integer.valueOf(map.get("cnumber"));

        return loginService.cnumberCompare(username, cnumber) ? new ResponseEntity<>("{}", HttpStatus.OK) :
                new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/newPW")
    public ResponseEntity<?> newPWChange(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder msg = userService.validation(bindingResult);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        loginService.passwordChange(userDTO);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
