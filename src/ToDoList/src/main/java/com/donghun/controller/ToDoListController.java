package com.donghun.controller;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.service.ToDoListService;
import com.donghun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Controller
@RequestMapping("/todolist")
public class ToDoListController {

    @Autowired
    UserService userService;

    @Autowired
    ToDoListService toDoListService;

    @GetMapping
    public String list(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        User user = userService.findUserId(currentUser.getUsername());
        model.addAttribute("todoList", toDoListService.findToDoList(user));
        return "todolist/list";
    }

    @PostMapping
    public ResponseEntity<?> postToDoList(@RequestBody @Valid ToDoList toDoList, BindingResult result,
                                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        if(result.hasErrors()) {
           userService.validation(result);
        }

        User user = userService.findUserId(currentUser.getUsername());
        toDoListService.PostToDoList(toDoList, user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/status/{idx}")
    public ResponseEntity<?> putStatus(@PathVariable("idx")Integer idx, @RequestBody ToDoList toDoList) {
        toDoListService.putStatusToDoList(idx, toDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putDescription(@PathVariable("idx")Integer idx, @RequestBody ToDoList toDoList) {
        toDoListService.putToDoList(idx, toDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteToDoList(@PathVariable("idx")Integer idx) {
        toDoListService.deleteToDoList(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
