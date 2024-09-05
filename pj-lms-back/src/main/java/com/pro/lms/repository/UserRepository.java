package com.pro.lms.repository;

import com.pro.lms.entity.Post;
import com.pro.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername (String username);
}
