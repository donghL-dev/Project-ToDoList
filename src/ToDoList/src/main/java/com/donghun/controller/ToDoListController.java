package com.donghun.controller;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.repository.ToDoListRepository;
import com.donghun.service.ToDoListService;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoListController {

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    UserService userService;

    @Autowired
    ToDoListService toDoListService;

    private User user;

    @GetMapping("/list")
    public String list(Model model) {
        if(user == null) {
            return "todolist/login";
        }
        else {
            model.addAttribute("todoList", toDoListService.findToDoList(user));

            System.out.println("현재 로그인한 유저의 Idx : " + user.getIdx());

            List<ToDoList> toDoLists = user.getToDoLists();
            if(toDoLists == null)
                System.out.println("현재 작성한 ToDo가 없습니다.");
            else {
                StringBuilder sb = new StringBuilder();
                for(ToDoList toDo : toDoLists)
                    sb.append(toDo.getIdx()).append(" ").append(toDo.getDescription()).append(" ").append(toDo.getStatus()).append(" ")
                    .append(toDo.getCreatedDate()).append(" ").append(toDo.getCompletedDate()).append("\n");
                System.out.println(sb.toString() + "--------------------------------------------");
            }

            return "todolist/list";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        user = null;
        return "todolist/login";
    }

    @PostMapping("/todolist/idxrequest")
    public ResponseEntity<?> postIdx(@RequestBody User user2) {
        user = userService.findUserName(user2.getName());
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


    @PostMapping("/todolist")
    public ResponseEntity<?> postToDoList(@RequestBody ToDoList toDoList) {
        toDoList.setCreatedDateNow();
        user.add(toDoList);
        toDoListRepository.save(toDoList);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/todolist/status/{idx}")
    public ResponseEntity<?> putStatus(@PathVariable("idx")Integer idx, @RequestBody ToDoList toDoList) {
        ToDoList persistToDoList = toDoListRepository.getOne(idx);
        persistToDoList.StatusUpdate(toDoList);
        toDoListRepository.save(persistToDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/todolist/{idx}")
    public ResponseEntity<?> putDescription(@PathVariable("idx")Integer idx, @RequestBody ToDoList toDoList) {
        ToDoList persistToDoList = toDoListRepository.getOne(idx);
        persistToDoList.update2(toDoList);
        toDoListRepository.save(persistToDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/todolist/{idx}")
    public ResponseEntity<?> deleteToDoList(@PathVariable("idx")Integer idx) {
        toDoListRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
