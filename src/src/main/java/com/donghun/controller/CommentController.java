package com.donghun.controller;

import com.donghun.domain.Comment;
import com.donghun.domain.CommentDTO;
import com.donghun.domain.ToDoList;
import com.donghun.repository.ToDoListRepository;
import com.donghun.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author dongh9508
 * @since  2019-04-12
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    ToDoListRepository toDoListRepository;

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody @Valid CommentDTO commentDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder msg = commentService.validation(bindingResult);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        ToDoList toDoList = toDoListRepository.getOne(commentDTO.getTodolistIdx());
        commentDTO = commentService.postComment(toDoList, commentDTO);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/status/{idx}")
    public ResponseEntity<?> putStatus(@PathVariable("idx")Long idx) {
        commentService.putStatusComment(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putContent(@PathVariable("idx")Long idx, @RequestBody @Valid CommentDTO commentDTO,
                                        BindingResult result) {
        if(result.hasErrors()) {
            StringBuilder msg = commentService.validation(result);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        commentService.putComment(idx, commentDTO.getContent());
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteToDoList(@PathVariable("idx")Long idx) {
        commentService.deleteToDoList(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
