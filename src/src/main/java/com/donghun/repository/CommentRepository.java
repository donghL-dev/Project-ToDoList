package com.donghun.repository;

import com.donghun.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dongh9508
 * @since  2019-04-20
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByContent(String content);
}
