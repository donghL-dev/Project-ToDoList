package com.donghun.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author dongh9508
 * @since  2019-04-12
 */
@Entity
@Setter
@Getter
@ToString
@Data
@Table
@NoArgsConstructor
public class Comment implements Comparable<Comment> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String content;

    @Column
    private Boolean status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column
    private LocalDateTime completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ToDoList toDoList;

    @Builder
    public Comment(String content, Boolean status, LocalDateTime createdDate, LocalDateTime modifiedDate, LocalDateTime completedDate, ToDoList toDoList) {
        this.content = content;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.completedDate = completedDate;
        this.toDoList = toDoList;
    }


    public void StatusUpdate(Comment comment) {
        this.status = comment.getStatus();
        this.completedDate = this.status ? LocalDateTime.now() : null;
    }

    public void update2(Comment comment) {
        this.content = comment.getContent();
        this.modifiedDate = LocalDateTime.now();
    }

    @Override
    public int compareTo(Comment o) {
        return this.idx < o.idx ? -1 : 1;
    }
}
