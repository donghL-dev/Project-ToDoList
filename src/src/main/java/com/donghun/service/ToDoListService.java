package com.donghun.service;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Service
public class ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    public List<ToDoList> findToDoList(User user){
        List<ToDoList> lists = toDoListRepository.findByUserOrderByIdx(user);
        for(ToDoList list : lists) {
            Collections.sort(list.getComments());
            list.setComments(list.getComments());
        }
        return lists;
    }

    public void PostToDoList(ToDoList toDoList, User user) {
        toDoList.setCreatedDateNow();
        user.add(toDoList);
        toDoListRepository.save(toDoList);
    }

    public void putToDoList(Integer idx, ToDoList toDoList) {
        ToDoList persistToDoList = toDoListRepository.getOne(idx);
        persistToDoList.update2(toDoList);
        toDoListRepository.save(persistToDoList);
    }

    public void putStatusToDoList(Integer idx, ToDoList toDoList) {
        ToDoList persistToDoList = toDoListRepository.getOne(idx);
        persistToDoList.StatusUpdate(toDoList);
        toDoListRepository.save(persistToDoList);
    }

    public void deleteToDoList(Integer idx) {
        toDoListRepository.deleteById(idx);
    }

}
