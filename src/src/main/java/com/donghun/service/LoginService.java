package com.donghun.service;

import com.donghun.domain.User;
import com.donghun.domain.UserDTO;
import com.donghun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class LoginService {

    @Autowired
    private MailSender sender;

    @Autowired
    private UserRepository userRepository;

    private Map<String, Integer> map = new HashMap<>();

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
        int cnumber = random.nextInt(9999 - 1111 + 1) + 1111;

        map.put(username, cnumber);
        msg.setFrom("dongh9508@gmail.com");
        msg.setTo(email);
        msg.setSubject("회신이 불가능한 메일입니다.");
        msg.setText("Certification Number : " + cnumber);
        this.sender.send(msg);
    }

    public Boolean cnumberCompare(String username, Integer cnumber) {
        Integer original_cnumber = map.get(username);
        return original_cnumber.equals(cnumber);
    }

    public void passwordChange(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }
}
