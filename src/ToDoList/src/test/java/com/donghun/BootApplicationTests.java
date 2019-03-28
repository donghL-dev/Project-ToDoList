package com.donghun;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.repository.ToDoListRepository;
import com.donghun.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void contextLoads() {
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("테스트코드로 작성한 todolist1");

        ToDoList toDoList2 = new ToDoList();
        toDoList2.setDescription("테스트코드로 작성한 todolist2");

        User user = new User();
        user.setName("테스트 코드로 생성한 유저");
        userRepository.save(user);

        user.add(toDoList);

        toDoListRepository.save(toDoList);

        user.add(toDoList2);

        toDoListRepository.save(toDoList2);

    }

}
