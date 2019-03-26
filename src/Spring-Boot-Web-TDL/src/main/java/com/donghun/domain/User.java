package com.donghun.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ToDoList> toDoLists = new ArrayList<>();

    @Builder
    public User(String name, String password, String email, List<ToDoList> toDoLists) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.toDoLists = toDoLists;
    }

    public void add(ToDoList toDoList) {
        toDoList.setUser(this);
        this.toDoLists.add(toDoList);
    }

}
