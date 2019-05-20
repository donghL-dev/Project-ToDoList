package com.donghun.controller;

import com.donghun.domain.User;
import com.donghun.domain.UserDTO;
import com.donghun.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test001RegisterGet() throws Exception {
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todolist/register"));
    }

    @Test
    public void test002RegisterPost() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("user1234");
        userDTO.setPassword("12345@A");
        userDTO.setEmail("user12345@email.com");

        // 생성하고자 하는 User의 생성을 위한 Post 테스트.
        mockMvcPerformPost(userDTO, 201);

        // 생성된 User가 데이터베이스에 존재하는지, UserDTO의 데이터대로 잘 저장되었는지에 대한 테스트.
        dataBaseCheck(userDTO.getId(), userDTO.getEmail());
    }

    @Test
    public void test003RegisterIdVaild() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("");
        userDTO.setEmail("donghun@email.com");
        userDTO.setPassword("12345@A");

        // User의 아이디가 빈 문자열일 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setId("use");

        // User의 아이디가 최소 길이보다 짧을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setId("abcdefgqwer222");

        // User의 아이디가 최대 길이보다 길 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setId("ABCDEF");

        // User의 아이디가 영문 대문자로 구성되어 있는 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setId("!@##$%^&*");

        // User의 아이디가 특수 문자로만 구성되어 있는 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setId("ABCD@@@@@");

        // User의 아이디가 영문 대문자 또는 특수문자일 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setId("donghun123");

        // User의 아이디 중복 테스트를 위해서 올바른 형식의 ID로 User 생성 테스트.
        mockMvcPerformPost(userDTO, 201);

        // 이미 생성된 User의 아이디로 생성할 경우의 아이디 중복검사 테스트.
        mockMvcPerformPost(userDTO, 403);

        // 생성된 User가 데이터베이스에 존재하는지, UserDTO의 데이터대로 잘 저장되었는지에 대한 테스트.
        dataBaseCheck(userDTO.getId(), userDTO.getEmail());
    }

    @Test
    public void test004RegisterEmailVaild() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("donghun1234");
        userDTO.setEmail("");
        userDTO.setPassword("12345@A");

        // User의 이메일이 빈 문자열일 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setEmail("asddd");

        // User의 이메일이 이메일 형식이 아닐 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setEmail("donghun1234@email.com");

        // User의 이메일 중복 테스트를 위해서 올바른 형식의 이메일로 User 생성 테스트.
        mockMvcPerformPost(userDTO, 201);

        // 이미 생성된 User의 이메일로 생성할 경우의 이메일 중복검사 테스트.
        mockMvcPerformPost(userDTO, 403);

        // 생성된 User가 데이터베이스에 존재하는지, UserDTO의 데이터대로 잘 저장되었는지에 대한 테스트.
        dataBaseCheck(userDTO.getId(), userDTO.getEmail());
    }

    @Test
    public void test005RegisterPasswordVaild() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("donghun1234");
        userDTO.setEmail("donghun1234@email.com");
        userDTO.setPassword("");

        // User의 비밀번호가 빈 문자열일 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("1@Ae");

        // User의 비밀번호가 최소 비밀번호 길이보다 짧을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("1@AeVVCZXZXASQWE12312423asdasd1312312asd12zcxzx222222222");

        // User의 비밀번호가 최대 비밀번호 길이보다 길 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("123456");

        // User의 비밀번호가 숫자로만 이루어져 있을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("AncdedAADDDD");

        // User의 비밀번호가 영문 대소문자로만 이루어져 있을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("@!@#$%^&*()");

        // User의 비밀번호가 특수문자로만 이루어져 있을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("AncdedAADDDD12345");

        // User의 비밀번호가 영문 대소문자와 숫자로만 이루어져 있을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("Ancded@#$!@");

        // User의 비밀번호가 영문 대소문자로와 특수기호로만 이루어져 있을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("12345@##!@#%%%^&");

        // User의 비밀번호가 숫자와 특수기호로만 이루어져 있을 경우의 테스트.
        mockMvcPerformPost(userDTO, 400);

        userDTO.setPassword("12345@A");

        // User의 비밀번호가 영문 대소문자, 숫자, 특수기호로 이루어져 있을 경우의 테스트.
        // 테스트 결과, 비밀번호가 올바르게 잘 구성되어서 User가 생성되어야 함.
        mockMvcPerformPost(userDTO, 201);

        // 생성된 User가 데이터베이스에 존재하는지, UserDTO의 데이터대로 잘 저장되었는지에 대한 테스트.
        dataBaseCheck(userDTO.getId(), userDTO.getEmail());
    }

    public void mockMvcPerformPost(UserDTO userDTO, int status) throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(statusCheck(status));
    }

    public ResultMatcher statusCheck(int status) {
        if(status == 201)
            return status().isCreated();
        else if(status == 403)
            return status().isForbidden();
        else
            return status().isBadRequest();
    }

    public void dataBaseCheck(String userId, String userEmail) {
        User user = userRepository.findById(userId);

        assertThat(user).isNotNull();
        assertThat(user.getId()) .isEqualTo(userId);
        assertThat(user.getEmail()).isEqualTo(userEmail);

        userRepository.deleteById(user.getIdx());
    }

}