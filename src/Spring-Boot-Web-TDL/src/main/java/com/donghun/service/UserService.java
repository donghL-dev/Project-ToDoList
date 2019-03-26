package com.donghun.service;

import com.donghun.domain.User;
import com.donghun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserName(String name) {
        return userRepository.findByName(name);
    }
}
