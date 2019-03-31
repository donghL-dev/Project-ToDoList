package com.donghun;

import com.donghun.domain.ToDoList;
import com.donghun.domain.User;
import com.donghun.repository.ToDoListRepository;
import com.donghun.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    private PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, ToDoListRepository toDoListRepository) throws Exception {
        return (args) -> {
            User user = userRepository.save(User.builder().id("havi").password(passwordEncoder.encode("test")).email("havi@gmail.com").build());

            IntStream.rangeClosed(1, 20).forEach(index -> toDoListRepository.save(ToDoList.builder().description("게시글" + index).status(false).createdDate(LocalDateTime.now()).user(user).build()));
        };
    };

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

}
