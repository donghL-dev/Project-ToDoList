package com.donghun.controller;

import com.donghun.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("todoList", toDoListService.findToDoList());
        return "todolist/list";
    }
}
