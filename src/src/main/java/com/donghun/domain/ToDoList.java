package com.donghun.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class ToDoList implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    @NotBlank(message = "내용을 입력하세요.")
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate, User user) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.user = user;
    }

    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

    public void StatusUpdate(ToDoList toDoList) {
        this.status = toDoList.getStatus();
        this.completedDate = this.status ? LocalDateTime.now() : null;
    }

    public void update2(ToDoList toDoList) {
        this.description = toDoList.getDescription();
    }
}