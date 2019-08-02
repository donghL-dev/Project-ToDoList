package com.donghun.service;

import com.donghun.domain.User;
import com.donghun.domain.UserDTO;
import com.donghun.domain.UserRole;
import com.donghun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dongh9508
 * @since 2019-03-29
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public User findUserId(String id) {
        return userRepository.findById(id);
    }

    public User findUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void DTOsave(UserDTO userDTO) {
        UserRole userRole = new UserRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRole.setRoleName("USER");
        User user = User.builder()
                .id(userDTO.getId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(Arrays.asList(userRole))
                .build();
        userRepository.save(user);
    }

    public StringBuilder validation(BindingResult bindingResult) {

        List<ObjectError> list = bindingResult.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(userRole -> authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName())));
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities);
    }

    public void updatePassword(String password, Integer idx) {
        userRepository.updatePassword(password, idx);
    }
}
