package com.donghun.Comment;

import com.donghun.domain.Comment;
import com.donghun.domain.CommentDTO;
import com.donghun.domain.ToDoList;
import com.donghun.domain.UserDTO;
import com.donghun.repository.CommentRepository;
import com.donghun.repository.ToDoListRepository;
import com.donghun.repository.UserRepository;
import com.donghun.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

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

    @Autowired
    private CommentRepository commentRepository;

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

        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트 투두리스트");
        toDoList.setStatus(false);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(status().isCreated());
    }

    @After
    public void deleteUser() {
        userRepository.deleteById(userRepository.findById(userDetails.getUsername()).getIdx());
        toDoListRepository.deleteById(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());
    }

    @Test // Comment Post 매핑시 등록 성공 테스트.
    public void test001PostCommentSucess() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트 댓글");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        Comment comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment).isNotNull();
        assertThat(comment.getContent()).isEqualTo(commentDTO.getContent());
        assertThat(comment.getToDoList().getIdx()).isEqualTo(commentDTO.getTodolistIdx());

        commentRepository.deleteById(comment.getIdx());
    }

    @Test // Comment Post 매핑시 빈문자열로 인한 등록 실패 테스트.
    public void test002PostCommentFailEmptyString() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isBadRequest());
    }

    @Test // Comment Post 매핑시 최대 길이 초과로 인한 등록 실패 테스트.
    public void test003PostCommentFailMaxLength() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isBadRequest());
    }

    @Test // Todo에 Comment 등록 후, ToDo의 Cooment List에 comment가 갯수만큼 반영됬는지 테스트.
    public void test004RelationshipBetweenCommentAndToDo() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트투두 댓글1");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        commentDTO.setContent("테스트투두 댓글2");

        CreateComment(commentDTO);

        commentDTO.setContent("테스트투두 댓글3");

        CreateComment(commentDTO);

        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트 투 두 리스트2");
        toDoList.setStatus(false);

        CreateToDo(toDoList);

        commentDTO.setContent("테스트투두2 댓글1");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투 두 리스트2").getIdx());

        CreateComment(commentDTO);

        commentDTO.setContent("테스트투두2 댓글2");

        CreateComment(commentDTO);

        toDoList.setDescription("테스트 투 두 리스트3");

        CreateToDo(toDoList);

        commentDTO.setContent("테스트투두3 댓글1");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투 두 리스트3").getIdx());

        CreateComment(commentDTO);

        assertThat(toDoListRepository.findByDescription("테스트 투두리스트").getComments()).hasSize(3);
        assertThat(toDoListRepository.findByDescription("테스트 투 두 리스트2").getComments()).hasSize(2);
        assertThat(toDoListRepository.findByDescription("테스트 투 두 리스트3").getComments()).hasSize(1);

        toDoListRepository.deleteById(toDoListRepository.findByDescription("테스트 투 두 리스트2").getIdx());
        toDoListRepository.deleteById(toDoListRepository.findByDescription("테스트 투 두 리스트3").getIdx());
    }

    @Test // Comment 등록 후, Comment 완료 여부 변경 확인 테스트.
    public void test005PutCommentStatusSucess() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트 댓글");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        Comment comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment).isNotNull();
        assertThat(comment.getCompletedDate()).isNull();
        assertThat(comment.getStatus()).isFalse();

        mockMvc.perform(put("/comment/status/" + comment.getIdx())
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(userDetails)))
                .andExpect(status().isOk());

        comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment.getStatus()).isTrue();
        assertThat(comment.getCompletedDate()).isNotNull();

        commentRepository.deleteById(comment.getIdx());
    }

    @Test // Comment 등록 후, Comment 내용 수정시 빈문자열 삽입 시 실패 테스트.
    public void test006PutCommentFailEmptyString() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트 댓글");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        Comment comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment).isNotNull();

        commentDTO.setContent("");

        mockMvc.perform(put("/comment/" + comment.getIdx())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isBadRequest());
    }

    @Test // Comment 등록 후, Comment 내용 수정시 문자열 길이 최대 초과 시 실패 테스트.
    public void test007PutCommentFailMaxLength() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트 댓글");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        Comment comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment).isNotNull();

        commentDTO.setContent("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");

        mockMvc.perform(put("/comment/" + comment.getIdx())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isBadRequest());
    }

    @Test // Comment 등록 후, Comment 내용 수정시 문자열 길이 최대 초과 시 실패 테스트.
    public void test008PutCommentSucess() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트 댓글");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        Comment comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment).isNotNull();

        commentDTO.setContent("수정된 테스트 댓글");

        mockMvc.perform(put("/comment/" + comment.getIdx())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isOk());

        comment = commentRepository.findByContent("수정된 테스트 댓글");

        assertThat(comment).isNotNull();
        assertThat(comment.getModifiedDate()).isNotNull();
    }

    @Test
    public void test009DeleteCommentSucess() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("테스트 댓글");
        commentDTO.setTodolistIdx(toDoListRepository.findByDescription("테스트 투두리스트").getIdx());

        CreateComment(commentDTO);

        Comment comment = commentRepository.findByContent("테스트 댓글");

        assertThat(comment).isNotNull();

        mockMvc.perform(delete("/comment/" + comment.getIdx())
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isOk());

        comment = commentRepository.findByContent("수정된 태스트 댓글");

        assertThat(comment).isNull();
    }

    public void CreateComment(CommentDTO commentDTO) throws Exception {
        mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isCreated());
    }

    public void CreateToDo(ToDoList toDoList) throws Exception {
        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList))
                .with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isCreated());
    }


}
