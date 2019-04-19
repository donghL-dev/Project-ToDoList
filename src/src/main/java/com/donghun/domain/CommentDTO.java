package com.donghun.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author dongh9508
 * @since  2019-04-12
 */
@Getter
@Setter
public class CommentDTO {

    @NotBlank
    @Length(min = 1)
    private String content;

    private Integer todolistIdx;

    private Long commentIdx;

    private LocalDateTime dateTime;

    private LocalDateTime modifyTime;

    public void readCommentInfo(Comment comment) {
        this.commentIdx = comment.getIdx();
        this.content = comment.getContent();
        this.dateTime = comment.getCreatedDate();
        this.modifyTime = comment.getModifiedDate();
    }
}
