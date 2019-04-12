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

    }

}
