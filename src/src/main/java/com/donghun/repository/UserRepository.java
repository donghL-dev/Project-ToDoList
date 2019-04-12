package com.donghun.repository;

import com.donghun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(String id);
    User findByEmail(String email);
}
