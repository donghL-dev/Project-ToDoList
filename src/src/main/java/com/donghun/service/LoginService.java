package com.donghun.service;

import com.donghun.domain.Mail;
import com.donghun.domain.PasswordResetDTO;
import com.donghun.domain.PasswordResetToken;
import com.donghun.domain.User;
import com.donghun.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author dongh9508
 * @since  2019-06-08
 */
@Service
public class LoginService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendMailId(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("dongh9508@gmail.com");
        msg.setTo(email);
        msg.setSubject("회신이 불가능한 메일입니다.");
        msg.setText(email + " 이메일을 사용하는 계정의 아이디 : " + username);
        this.sender.send(msg);
    }

    public void sendMailPW(Mail mail, PasswordResetToken token, User user, HttpServletRequest request) {
        mail.setFrom("dongh9508@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "https://dongh9508.hopto.org:8081");

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/login/reset-password?token=" + token.getToken());
        mail.setModel(model);

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = templateEngine.process("email/email-template", context);

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            sender.send(message);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> updatePassword(PasswordResetDTO form) {
        PasswordResetToken token = null;

        if(tokenRepository.findByToken(form.getToken()) != null) {
            token = tokenRepository.findByToken(form.getToken());
            if(token.isExpired()) {
                tokenRepository.delete(token);
                return new ResponseEntity<>("Token has expired, please request a new password reset.",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            String error = "올바르지 않은 접근으로 인해 정상적으로 비밀번호를 변경할 수 없습니다.";
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User user = token.getUser();
        String updatePassword = passwordEncoder.encode(form.getPassword());
        userService.updatePassword(updatePassword, user.getIdx());
        tokenRepository.delete(token);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
