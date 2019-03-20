package com.donghun;

import com.donghun.domain.ToDoList;
import com.donghun.repository.ToDoListRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ToDoListRepository toDoListRepository) throws Exception {
        return (args) -> {



            IntStream.rangeClosed(1, 5).forEach(index -> toDoListRepository.save(ToDoList.builder()
                    .description("스프링 부트로 구현하는 To Do List " + index)
                    .status(false)
                    .createdDate(LocalDateTime.now())
                    .build())
            );

            IntStream.rangeClosed(1, 5).forEach(index -> toDoListRepository.save(ToDoList.builder()
                    .description("완료된 항목" + index)
                    .status(true)
                    .createdDate(LocalDateTime.now())
                    .completedDate(LocalDateTime.now())
                    .build())
            );
        };
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

}
