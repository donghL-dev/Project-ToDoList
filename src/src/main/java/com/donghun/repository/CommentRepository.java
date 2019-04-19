package com.donghun.repository;

import com.donghun.domain.Comment;
import com.donghun.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByToDoListOrderByIdx(ToDoList toDoList);
}
