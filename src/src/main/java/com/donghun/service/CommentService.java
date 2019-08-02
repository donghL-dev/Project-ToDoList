package com.donghun.service;

import com.donghun.domain.Comment;
import com.donghun.domain.CommentDTO;
import com.donghun.domain.ToDoList;
import com.donghun.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dongh9508
 * @since 2019-04-12
 */
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public StringBuilder validation(BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    public CommentDTO postComment(ToDoList toDoList, CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setStatus(false);
        comment.setCreatedDate(LocalDateTime.now());
        toDoList.add(comment);
        commentRepository.save(comment);
        commentDTO.readCommentInfo(comment);
        return commentDTO;
    }

    public void putComment(Long idx, String comment) {
        Comment persistComment = commentRepository.getOne(idx);
        persistComment.update2(comment);
        commentRepository.save(persistComment);
    }

    public void putStatusComment(Long idx) {
        Comment persistComment = commentRepository.getOne(idx);
        persistComment.StatusUpdate(persistComment.getStatus());
        commentRepository.save(persistComment);
    }

    public void deleteToDoList(Long idx) {
        commentRepository.deleteById(idx);
    }

}
