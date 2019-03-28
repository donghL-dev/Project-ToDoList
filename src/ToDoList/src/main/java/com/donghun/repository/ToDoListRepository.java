package com.donghun.repository;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    List<ToDoList> findByUserOrderByIdx(User user);
}
