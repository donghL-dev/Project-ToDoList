package com.donghun;

import com.donghun.domain.User;
import com.donghun.domain.UserDTO;
import com.donghun.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // root url 접근시 get 테스팅.
    @Test
    public void test001GetRoot() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(redirectedUrl("/todolist"))
                .andExpect(status().isFound());
    }

    // login 페이지 접근시의 get 테스팅
    @Test
    public void  test002GetLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 회원가입 페이지 접근시의 get 테스팅
    @Test
    public void test003GetRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 회원가입 페이지 post 테스팅
    @Test
    public void test004PostRegister() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("test_user");
        userDTO.setPassword("12345");
        userDTO.setEmail("test_user@email.com");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        User user = userRepository.findById("test_user");

        assertThat(user).isNotNull();
        assertThat(user.getId()) .isEqualTo("test_user");
        assertThat(user.getEmail()).isEqualTo("test_user@email.com");

        userRepository.deleteById(user.getIdx());
    }

    // 회원가입 시 ID 유효성 검증 테스팅
    @Test
    public void test005RegisterVaildId() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("use");
        userDTO.setPassword("12345");
        userDTO.setEmail("test_user@email.com");

        // 길이보다 모자를 때 생성 테스트.
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // 미 입력시 생성 테스트
        userDTO.setId("");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // 회원가입 시 PW 유효성 검증 테스팅
    @Test
    public void test006RegisterVaildPW() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("user");
        userDTO.setPassword("12");
        userDTO.setEmail("test_user@email.com");

        // 길이보다 모자를 때 생성 테스트.
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // 미 입력시 생성 테스트
        userDTO.setPassword("");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // 회원가입 시 Email 유효성 검증 테스팅
    @Test
    public void test007RegisterVaildEmail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("user");
        userDTO.setPassword("12345");
        userDTO.setEmail("test");

        // 길이보다 모자를 때 생성 테스트.
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // 미 입력시 생성 테스트
        userDTO.setEmail("");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
