package com.donghun.ForgotAccount;

import com.donghun.domain.PasswordForgotDTO;
import com.donghun.domain.PasswordResetDTO;
import com.donghun.domain.PasswordResetToken;
import com.donghun.repository.PasswordResetTokenRepository;
import com.donghun.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForgotAccountTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    private MockHttpSession mockHttpSession;

    @Before
    public void setup() throws Exception {
        mockHttpSession = new MockHttpSession();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/").session(mockHttpSession))
                .alwaysDo(print())
                .build();

    }

    @Test // 아이디를 찾을 때, 이메일을 올바르게 입력했는지에 대한 테스트.
    public void test001ForgotIdInputExistEmail() throws Exception {
        String email = "zsc621@ks.ac.kr";

        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk());
    }

    @Test // 아이디를 찾을 때, 이메일을 잘못된 형식으로 입력했을 때의 테스트.
    public void test002ForgotIdInputInvalidEmailFormat() throws Exception {
        String email = "";

        // 이메일을 공백으로 입력했을 때의 테스트.
        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());

        email = "abcdefg";

        // 이메일을 이메일 형식으로 입력하지 않았을 때의 테스트.
        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());

        email = "abcdefg@gmail.com";

        // 이메일을 존재하지 않는 이메일로 입력했을 때의 테스트.
        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }


    @Test // 아이디를 찾을 때, 이메일 형식이 맞고, 존재하는 이메일일 떄 이메일로 아이디를 전송하는 테스트.
    public void test003ForgotIdSendEmailSucess() throws Exception {
        String email = "zsc621@ks.ac.kr";

        mockMvc.perform(post("/login/emailsendId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk());
    }


    @Test // 비밀번호를 찾을 때, 아이디가 존재할 때의 테스트.
    public void test004ForgotPasswordInputIdExist() throws Exception {
        String id = "zsc621";

        mockMvc.perform(post("/login/idcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(id))
                .andExpect(status().isOk());
    }

    @Test // 비밀번호를 찾을 때, 아이디를 잘못된 형식으로 입력했을 때의 테스트.
    public void test005ForgotPasswordInputInvalidIdFormat() throws Exception {
        String id = "";

        // 아이디를 공백으로 입력했을 떼의 테스트.
        mockMvc.perform(post("/login/idcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(id))
                .andExpect(status().isBadRequest());

        id = "zsc12345";

        // 존재하지 않는 아이디를 입력했을 때의 테스트.
        mockMvc.perform(post("/login/idcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(id))
                .andExpect(status().isBadRequest());
    }


    @Test // 비밀번호를 찾을 때, 올바른 아이디와 이메일을 입력했을 때의 이메일 전송 테스트.
    public void test06ForgotPasswordSendEmail() throws Exception {
        PasswordForgotDTO forgotDTO = new PasswordForgotDTO();
        forgotDTO.setId("zsc621");
        forgotDTO.setEmail("zsc621@ks.ac.kr");

        mockMvc.perform(post("/login/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotDTO)))
                .andExpect(status().isOk());
    }

    @Test // 비밀번호를 찾을 때, 올바르지 않은 아이디와 이메일을 입력했을 때의 이메일 전송 테스트.
    public void test07ForgotPasswordSendEmailInvalidFormat() throws Exception {
        PasswordForgotDTO forgotDTO = new PasswordForgotDTO();
        forgotDTO.setId("zsc621222");
        forgotDTO.setEmail("zsc621@ks.ac.kr");

        // 아이디가 올바르지 않을 떄의 테스트.
        mockMvc.perform(post("/login/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotDTO)))
                .andExpect(status().isBadRequest());

        forgotDTO.setId("zsc621");
        forgotDTO.setEmail("zsc6212222");

        // 이메일이 올바르지 않을 때의 테스트.
        mockMvc.perform(post("/login/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotDTO)))
                .andExpect(status().isBadRequest());

        forgotDTO.setId("user1");
        forgotDTO.setEmail("zsc621@ks.ac.kr");

        // 아이디와 이메일이 올바르고 존재하나, 아이디와 이메일이 일치하지 않을 때의 이메일 전송 테스트.
        mockMvc.perform(post("/login/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotDTO)))
                .andExpect(status().isBadRequest());

    }


    @Test // 비밀번호를 재설정 할 때, 재설정 페이지에 토큰이 올바르지 않았을 때의 테스트.
    public void test08GetResetPasswordPageWithNotExistToken() throws Exception {

        // 재설정 페이지에 토큰이 포함되지 않은 URL로 접근시의 테스트.
        mockMvc.perform(get("/login/reset-password"))
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());

        // 재설정 페이지에 URL에 토큰은 포함하나, 토큰이 올바르지 않을 때의 테스트.
        mockMvc.perform(get("/login/reset-password?token=invalid-token"))
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());

        // 재설정 페이지에 URL에 토큰은 포함하나, 토큰이 만료되었을 경우의 테스트.
        mockMvc.perform(get("/login/reset-password?token=af834bb3-3aa4-4f80-8ccf-43a9284ce4ca"))
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());
    }


    @Test // 비밀번호를 재설정 할 때, 재설정 페이지에 접근 시, 올바른 토큰을 포함한 URL 접근시의 테스트.
    public void test09GetResetPasswordPageWithValidToken() throws Exception {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(userRepository.findById("zsc621"));
        token.setExpiryDate(30);
        tokenRepository.save(token);

        mockMvc.perform(get("/login/reset-password?token=" + token.getToken()))
                .andExpect(model().attributeExists("token"))
                .andExpect(status().isOk());

        tokenRepository.delete(token);
    }

    @Test // 비밀번호를 재설정 할 때, 토큰은 올바르나 입력한 비밀번호가 올바르지 않을 때의 테스트.
    public void test010PostResetPasswordWithInvalidPasswordFormat() throws Exception {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(userRepository.findById("zsc621"));
        token.setExpiryDate(30);
        tokenRepository.save(token);

        PasswordResetDTO resetDTO = new PasswordResetDTO();
        resetDTO.setToken(token.getToken());
        resetDTO.setPassword("");
        resetDTO.setConfirmPassword("");

        // 비밀번호가 공백일 때의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        resetDTO.setPassword("1234asd");
        resetDTO.setConfirmPassword("1234asd");

        // 비밀번호가 올바른 형식으로 입력되지 않았을 때의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        resetDTO.setPassword("12a@");
        resetDTO.setConfirmPassword("12a@");

        // 비밀번호가 5자리 미만으로 입력으되었을 떄의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        resetDTO.setPassword("abcdefgkiaosldnslwlslsleiaksnjdi2345667756423442@#!$%^&*");
        resetDTO.setConfirmPassword("abcdefgkiaosldnslwlslsleiaksnjdi2345667756423442@#!$%^&*");

        // 비밀번호가 22자리 이상으로 입력되었을 때의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        resetDTO.setPassword("123455asdf@#$");
        resetDTO.setConfirmPassword("1234asd@$%^&");

        // 비밀번호와 비밀번호 재확인 부분이 일치하지 않을 때의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        tokenRepository.delete(token);
    }

    @Test // 비밀번호를 재설정 할 때, 비밀번호 형식은 올바르나, 토큰이 올바르지 않을때의 테스트.
    public void test011PostResetPasswordWithInvalidToken() throws Exception {
        PasswordResetDTO resetDTO = new PasswordResetDTO();
        resetDTO.setToken("");
        resetDTO.setPassword("1108ablq@#$%^");
        resetDTO.setConfirmPassword("1108ablq@#$%^");

        // 토큰이 공백일 때의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        resetDTO.setToken("Invalid Token");

        // 존재하지 않는 토큰일 떄의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());

        resetDTO.setToken("af834bb3-3aa4-4f80-8ccf-43a9284ce4ca");

        // 토큰은 존재하나, 토큰이 만료되었을 경우의 테스트.
        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test // 비밀번호를 재설정 할 때, 토큰과 입력한 비밀번호 모두 올바를 때의 정상적인 재설정 테스트.
    public void test012PostResetPasswordWithValidPasswordFormatAndToken() throws Exception {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(userRepository.findById("zsc621"));
        token.setExpiryDate(30);
        tokenRepository.save(token);

        PasswordResetDTO resetDTO = new PasswordResetDTO();
        resetDTO.setToken(token.getToken());
        resetDTO.setPassword("1108asd@#!");
        resetDTO.setConfirmPassword("1108asd@#!");

        mockMvc.perform(post("/login/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetDTO)))
                .andExpect(status().isOk());

        tokenRepository.delete(token);
    }

}
