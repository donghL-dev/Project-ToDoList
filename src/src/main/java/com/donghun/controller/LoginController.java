package com.donghun.controller;

import com.donghun.domain.*;
import com.donghun.repository.PasswordResetTokenRepository;
import com.donghun.service.LoginService;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @ModelAttribute("passwordResetForm")
    public PasswordResetDTO passwordReset() {
        return new PasswordResetDTO();
    }

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
        String username = null;

        if(userService.findUserEmail(email) != null)
            username = userService.findUserEmail(email).getId();
        else
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);

        loginService.sendMailId(email, username);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/login/forgot-password")
    public ResponseEntity<?> emailSendPW(@RequestBody @Valid PasswordForgotDTO passwordForgotDTO,
                                         BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()) {
            StringBuilder msg = userService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        User user = null;

        if(userService.findUserEmail(passwordForgotDTO.getEmail()) != null) {
            user = userService.findUserEmail(passwordForgotDTO.getEmail());
        }
        else {
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        loginService.sendMailPW(mail, token, user, request);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping("/login/reset-password")
    public String displayResetPasswordPage(@RequestParam(required = false) String token, Model model) {

        PasswordResetToken resetToken = null;

        if(tokenRepository.findByToken(token) != null)
            resetToken = tokenRepository.findByToken(token);

        if(resetToken == null) {
            model.addAttribute("error", "Could not find password reset token.");
        } else if(resetToken.isExpired()) {
            model.addAttribute("error", "Token has expired, please request a new password reset.");
        } else {
            model.addAttribute("token", resetToken.getToken());
        }

        return "todolist/reset-password";
    }

    @PostMapping("/login/reset-password")
    @Transactional
    public ResponseEntity<?> handlePasswordReset(@RequestBody @Valid PasswordResetDTO form, BindingResult result) {

        if(result.hasErrors()) {
            StringBuilder msg = userService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        } else if(!form.getPassword().equals(form.getConfirmPassword())) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return loginService.updatePassword(form);
    }


}
