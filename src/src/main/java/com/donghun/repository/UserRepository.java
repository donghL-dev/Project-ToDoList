package com.donghun.repository;

import com.donghun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(String id);
    User findByEmail(String email);

    @Modifying
    @Query("update User u set u.password = :password where u.idx = :idx")
    void updatePassword(@Param("password") String password, @Param("idx") Integer idx);
}
