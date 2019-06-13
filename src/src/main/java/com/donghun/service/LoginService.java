package com.donghun.service;

import com.donghun.domain.FindPasswordDTO;
import com.donghun.domain.User;
import com.donghun.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author dongh9508
 * @since  2019-06-08
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginService {

    @Autowired
    private MailSender sender;

    @Autowired
    private UserRepository userRepository;

    private String id;

    private Integer cnumber;

    private Boolean status = false;

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCnumber() {
        return cnumber;
    }

    public void sendMailId(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("dongh9508@gmail.com");
        msg.setTo(email);
        msg.setSubject("회신이 불가능한 메일입니다.");
        msg.setText(email + " 이메일을 사용하는 계정의 아이디 : " + username);
        this.sender.send(msg);
    }

    public void sendMailPW(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        Random random = new Random();
        this.cnumber = random.nextInt(9999 - 1111 + 1) + 1111;
        this.id = username;

        msg.setFrom("dongh9508@gmail.com");
        msg.setTo(email);
        msg.setSubject("회신이 불가능한 메일입니다.");
        msg.setText("Certification Number : " + this.cnumber);
        this.sender.send(msg);
    }

    public Boolean cnumberCompare(Integer cnumber) {

        if (this.cnumber.equals(cnumber))
            this.status = true;
        return this.cnumber.equals(cnumber);
    }

    public ResponseEntity<?> passwordChange(String password) {
        if(!this.status) {
            System.out.println("status의 상태는 = " + this.status);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(this.id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
