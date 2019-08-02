package com.donghun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongh9508
 * @since 2019-03-29
 */

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "idx")
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ToDoList> toDoLists = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idx")
    private List<UserRole> roles;

    @Builder
    public User(String id, String password, String email, List<ToDoList> toDoLists, List<UserRole> roles) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.toDoLists = toDoLists;
        this.roles = roles;
    }

    public void add(ToDoList toDoList) {
        toDoList.setUser(this);
        this.toDoLists.add(toDoList);
    }
}
