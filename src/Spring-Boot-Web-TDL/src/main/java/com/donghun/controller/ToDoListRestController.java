package com.donghun.controller;

import com.donghun.domain.ToDoList;
import com.donghun.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todolist")
public class ToDoListRestController {

    @Autowired
    ToDoListRepository toDoListRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoards() {
        List<ToDoList> toDoLists = toDoListRepository.findAll();
        return ResponseEntity.ok(toDoLists);
    }

    @PostMapping
    public ResponseEntity<?> postToDoList(@RequestBody ToDoList toDoList) {
        toDoList.setCreatedDateNow();
        toDoListRepository.save(toDoList);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/status/{idx}")
    public ResponseEntity<?> putStatus(@PathVariable("idx")Integer idx, @RequestBody ToDoList toDoList) {
        ToDoList persistToDoList = toDoListRepository.getOne(idx);
        persistToDoList.StatusUpdate(toDoList);
        toDoListRepository.save(persistToDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putDescription(@PathVariable("idx")Integer idx, @RequestBody ToDoList toDoList) {
        ToDoList persistToDoList = toDoListRepository.getOne(idx);
        persistToDoList.update2(toDoList);
        toDoListRepository.save(persistToDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteToDoList(@PathVariable("idx")Integer idx) {
        toDoListRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
