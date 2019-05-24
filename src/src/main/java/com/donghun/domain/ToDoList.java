package com.donghun.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Length(max = 255, min = 1, message = "ToDo는 1~255 내에서 등록해야 합니다.")
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

    @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate, User user) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.user = user;
    }

    public void add(Comment comment) {
        comment.setToDoList(this);
        this.comments.add(comment);
    }

    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

    public void StatusUpdate(boolean bls) {
        this.status = !bls;
        this.completedDate = this.status ? LocalDateTime.now() : null;
    }

    public void update2(String description) {
        this.description = description;
    }
}
