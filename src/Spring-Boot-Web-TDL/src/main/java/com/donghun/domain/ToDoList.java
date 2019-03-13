package com.donghun.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public class ToDoList implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
    }
}
