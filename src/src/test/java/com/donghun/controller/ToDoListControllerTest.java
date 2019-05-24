package com.donghun.controller;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.domain.UserDTO;
import com.donghun.repository.ToDoListRepository;
import com.donghun.repository.UserRepository;
import com.donghun.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoListControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private UserService userService;

    private UserDetails userDetails;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setId("user1234");
        userDTO.setPassword("12345@A");
        userDTO.setEmail("user12345@email.com");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        userDetails = userService.loadUserByUsername(userDTO.getId());
    }

    @After
    public void deleteUser() {
        userRepository.deleteById(userRepository.findById("user1234").getIdx());
    }

    @Test
    public void test001GetToDoList() throws Exception {
        mockMvc.perform(get("/").with(user(userDetails)))
                .andExpect(redirectedUrl("/todolist"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/todolist").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("todolist/list"))
                .andExpect(model().attributeExists("todoList"))
                .andExpect(authenticated());
    }

    @Test
    public void test002PostToDoListFailEmptyString() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("");
        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test003PostToDoListFailMaxLengthString() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test004PostToDoListSucess() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트 투두리스트");
        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        toDoList = toDoListRepository.findByDescription("테스트 투두리스트");

        assertThat(toDoList).isNotNull();
        assertThat(toDoList.getDescription()).isEqualTo("테스트 투두리스트");
        assertThat(toDoList.getCreatedDate()).isNotNull();
        assertThat(toDoList.getCompletedDate()).isNull();
        assertThat(toDoList.getStatus()).isFalse();
        assertThat(toDoList.getUser().getIdx()).isEqualTo(userRepository.findById(userDetails.getUsername()).getIdx());

        toDoListRepository.deleteById(toDoList.getIdx());
    }

    @Test
    public void test005PutToDoListStatus() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트 투두리스트");
        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        toDoList = toDoListRepository.findByDescription("테스트 투두리스트");

        assertThat(toDoList.getStatus()).isFalse();
        assertThat(toDoList.getCompletedDate()).isNull();

        mockMvc.perform(put("/todolist/status/" + toDoList.getIdx())
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetails)))
                .andExpect(status().isOk());

        toDoList = toDoListRepository.findByDescription("테스트 투두리스트");

        assertThat(toDoList.getStatus()).isTrue();
        assertThat(toDoList.getCompletedDate()).isNotNull();

        toDoListRepository.deleteById(toDoList.getIdx());
    }

    @Test
    public void test006DeleteToDoList() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트 투두리스트");
        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        toDoList = toDoListRepository.findByDescription("테스트 투두리스트");
        assertThat(toDoList).isNotNull();

        mockMvc.perform(delete("/todolist/" + toDoList.getIdx())
                .with(user(userDetails)))
                .andExpect(status().isOk());

        toDoList = toDoListRepository.findByDescription("테스트 투두리스트");
        assertThat(toDoList).isNull();
    }

    @Test
    public void test007PutToDoListDescription() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트 투두리스트");
        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(status().isCreated());

        toDoList = toDoListRepository.findByDescription("테스트 투두리스트");

        assertThat(toDoList).isNotNull();

        String str = "수정된 테스트 투두리스트";

        mockMvc.perform(put("/todolist/" + toDoList.getIdx())
                .contentType(MediaType.APPLICATION_JSON)
                .content(str)
                .with(user(userDetails)))
                .andExpect(status().isOk());

        ToDoList toDoList2 = toDoListRepository.findByDescription("수정된 테스트 투두리스트");

        assertThat(toDoList2).isNotNull();
        assertThat(toDoList2.getDescription()).isEqualTo(str);
        assertThat(toDoList2.getDescription()).isNotEqualTo(toDoList.getDescription());
        assertThat(toDoList2.getIdx()).isEqualTo(toDoList.getIdx());

        toDoListRepository.deleteById(toDoList2.getIdx());
    }


}