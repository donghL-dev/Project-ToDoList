package com.donghun.ForgotAccount;

import com.donghun.domain.PasswordForgotDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

    @Test // 아이디를 찾을 때, 이메일을 공백으로 입력했을 때의 테스트.
    public void test002ForgotIdInputEmailEmptyString() throws Exception {
        String email = "";

        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }

    @Test // 아이디를 찾을 때, 이메일을 이메일 형식으로 입력하지 않았을 때의 테스트.
    public void test003ForgotIdInpuNotEmailFormat() throws Exception {
        String email = "abcdefg";

        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }

    @Test // 아이디를 찾을 때, 이메일을 존재하지 않는 이메일을 입력했을 때의 테스트.
    public void test004ForgotIdInputNotExistEmail() throws Exception {
        String email = "abcdefg@gmail.com";

        mockMvc.perform(post("/login/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }

    @Test // 아이디를 찾을 때, 이메일 형식이 맞고, 존재하는 이메일일 떄 이메일로 아이디를 전송하는 테스트.
    public void test005ForgotIdSendEmailSucess() throws Exception {
        String email = "zsc621@ks.ac.kr";

        mockMvc.perform(post("/login/emailsendId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk());
    }

    @Test // 아이디를 찾을 때, 이메일이 형식에는 맞으나, 존재하지 않을 때, 이메일로 아이디를 전송하는 테스트.
    public void test006ForgotIdSendEmailAndNotExistEmail() throws Exception {
        String email = "abcd1234@ks.ac.kr";

        mockMvc.perform(post("/login/emailsendId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }

    @Test // 비밀번호를 찾을 때, 아이디가 존재할 때의 테스트.
    public void test007ForgotPasswordInputIdExist() throws Exception {
        String email = "zsc621";

        mockMvc.perform(post("/login/idcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isOk());
    }

    @Test // 비밀번호를 찾을 때, 아이디를 공백으로 입력했을 때의 테스트.
    public void test008ForgotPasswordInputIdEmptyString() throws Exception {
        String email = "";

        mockMvc.perform(post("/login/idcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }

    @Test // 비밀번호를 찾을 때, 존재하지 않는 아이디를 입력했을 때의 테스트.
    public void test009ForgotPasswordInputIdNotExist() throws Exception {
        String email = "zsc12345";

        mockMvc.perform(post("/login/idcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(email))
                .andExpect(status().isBadRequest());
    }

    @Test // 비밀번호를 찾을 때, 올바른 아이디와 이메일을 입력했을 때의 이메일 전송 테스트.
    public void test010ForgotPasswordSendEmail() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "zsc621");
        map.put("email", "zsc621@ks.ac.kr");

        mockMvc.perform(post("/login/emailsendPW")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk());
    }

    @Test // 비밀번호를 찾을 때, 올바르지 않은 아이디와 이메일을 입력했을 때의 이메일 전송 테스트.
    public void test011ForgotPasswordSendEmailInvalidformat() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "zsc621222");
        map.put("email", "zsc621@ks.ac.kr");

        // 아이디가 올바르지 않을 떄의 테스트.
        mockMvc.perform(post("/login/emailsendPW")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isBadRequest());

        map.put("id", "zsc621");
        map.put("email", "zsc6212222");

        // 이메일이 올바르지 않을 때의 테스트.
        mockMvc.perform(post("/login/emailsendPW")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isBadRequest());
    }

    @Test // 비밀번호를 찾을 때, 아이디와 이메일이 올바르나, 아이디와 이메일이 일치하지 않을 때의 이메일 전송 테스트.
    public void test012ForgotPasswordSendEmailAndIdNotEquals() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "user1");
        map.put("email", "zsc621@ks.ac.kr");

        mockMvc.perform(post("/login/emailsendPW")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isBadRequest());
    }

//    @Test // 인증번호 일치 후, 비밀번호 리셋 진행 중, 비밀번호가 올바르지 않을 때의 테스트.
//    public void test013PasswordRestInvaild() throws Exception {
//        MockHttpSession mockHttpSession = new MockHttpSession();
//        PasswordForgotDTO find = new PasswordForgotDTO();
//        find.setPassword("");
//        find.setConfirmPassword("");
//
//
//        // 비밀번호가 공백일 떄의 테스트.
//        mockMvc.perform(post("/login/newPW")
//                .session(mockHttpSession)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(find)))
//                .andExpect(status().isBadRequest());
//
//        find.setPassword("123asd");
//        find.setConfirmPassword("123asd");
//
//        // 비밀번호가 올바른 형식으로 입력하지 않았을 때의 테스트.
//        mockMvc.perform(post("/login/newPW")
//                .session(mockHttpSession)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(find)))
//                .andExpect(status().isBadRequest());
//
//        find.setPassword("12a@");
//        find.setConfirmPassword("12a@");
//
//        // 비밀번호가 5자리 미만일 때의 테스트.
//        mockMvc.perform(post("/login/newPW")
//                .session(mockHttpSession)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(find)))
//                .andExpect(status().isBadRequest());
//
//        find.setPassword("abcdefgkiaosldnslwlslsleiaksnjdi2345667756423442@#!$%^&*");
//        find.setConfirmPassword("abcdefgkiaosldnslwlslsleiaksnjdi2345667756423442@#!$%^&*");
//
//        // 비밀번호가 22자리 이상일 때의 테스트.
//        mockMvc.perform(post("/login/newPW")
//                .session(mockHttpSession)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(find)))
//                .andExpect(status().isBadRequest());
//
//        find.setPassword("123455asdf@#$");
//        find.setConfirmPassword("1234asd@$%^&");
//
//        // 비밀번호와 비밀번호 재확인 부분이 일치하지 않을 때의 테스트.
//        mockMvc.perform(post("/login/newPW")
//                .session(mockHttpSession)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(find)))
//                .andExpect(status().isBadRequest());
//    }

}
