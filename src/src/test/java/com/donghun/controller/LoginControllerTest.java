package com.donghun.controller;

import com.donghun.domain.User;
import com.donghun.domain.UserDTO;
import com.donghun.repository.UserRepository;
import com.donghun.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void registerUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("user1234");
        userDTO.setPassword("12345@A");
        userDTO.setEmail("user12345@email.com");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        // 이런 형식을 통해 MockMvcBuilders의 기본 요청을 사용하여 모든 요청에 ​대해 특정 사용자로 실행되도록 할 수 있디.
        /*
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/").with(user("user25444").roles("User")))
                .apply(springSecurity())
                .build();
        */
    }

    @After
    public void deleteUser() {
        userRepository.deleteById(userRepository.findById("user1234").getIdx());
    }

    /*
    @Test
    @WithMockUser
    public void test001SecurityLoginTest() throws Exception {
        User user = userRepository.findById("user1234");

        assertThat(user).isNotNull();

        mockMvc.perform(get("/todolist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todolist/list"));

    }
    */

    @Test
    public void test001LoginGetTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todolist/login"));
    }

    @Test
    public void test002LoginSucessTest01() throws Exception {
        mockMvc.perform(get("/todolist"))
                .andDo(print())
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user1234")
                .param("password", "12345@A"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void test003LoginSucessTest02() throws Exception {
        mockMvc.perform(get("/todolist").with(user("user1234")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("todolist/list"));

        mockMvc.perform(get("/todolist").with(user("user1234").password("12345@A")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("todolist/list"));

        mockMvc.perform(get("/todolist").with(user("user1234").password("12345@A").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(view().name("todolist/list"));
    }

//    // httpBasic() 을 이용한 로그인 성공 테스트.
//    @Test
//    public void test004LoginSucessTest03() throws Exception {
//        mockMvc.perform(get("/todolist").with(httpBasic("user1234", "12345@A")))
//                .andDo(print())
//                .andExpect(authenticated())
//                .andExpect(status().isOk())
//                .andExpect(view().name("todolist/list"));
//    }

    @Test
    public void test004LoginSucessTest003() throws Exception {
        UserDetails userDetails = userService.loadUserByUsername("user1234");

        assertThat(userDetails).isNotNull();

        mockMvc.perform(get("/todolist").with(user(userDetails)))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("todolist/list"));
    }

    @Test
    public void test005LoginSucessTest004() throws Exception {
        mockMvc.perform(formLogin().user("user1234").password("12345@A"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void test006LoginFailTest() throws Exception {

        // 아이디가 올바르지 않을 경우의 테스트
        mockMvc.perform(formLogin().user("user12345AAAAA").password("12345@A"))
                .andDo(print())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());

        // 비밀번호가 올바르지 않을 경우의 테스트
        mockMvc.perform(formLogin().user("user12345").password("12345@A22222222"))
                .andDo(print())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }

    @Test
    public void test007LogoutTest() throws Exception {
        mockMvc.perform(logout("/logout"))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void test008LoginSessionTest() throws Exception {

        // 올바르게 로그인 했을 경우, 로그인한 유저에 대한 세션을 얻을 수 있다.
        HttpSession httpSession = mockMvc.perform(formLogin().user("user1234").password("12345@A"))
                                         .andDo(print())
                                         .andExpect(authenticated())
                                         .andReturn()
                                         .getRequest()
                                         .getSession(false);

        // 올바르게 로그인 한 유저의 세션을 통해서 todolist에 접근할 수 있다.
        mockMvc.perform(get("/todolist").session((MockHttpSession) httpSession))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todolist/list"));

        // 존재하지 않는 유저의 경우, 올바른 세션을 얻을 수 없다.
        httpSession = mockMvc.perform(formLogin().user("user1234555").password("asdasd123134"))
                             .andDo(print())
                             .andExpect(unauthenticated())
                             .andReturn()
                             .getRequest()
                             .getSession(false);

        // 존재하지 않는 유저의 세션을 통해서는 todolist에 접근할 수 없다.
        mockMvc.perform(get("/todolist").session((MockHttpSession) httpSession))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void test008LoginCookieTest() throws Exception {
        Cookie[] httpCookie = mockMvc.perform(formLogin().user("user1234").password("12345@A"))
                                     .andDo(print())
                                     .andExpect(authenticated())
                                     .andReturn()
                                     .getRequest()
                                     .getCookies();

        assertThat(httpCookie).isNull();
    }


}