package com.pro.lms.repository;

import com.pro.lms.entity.LmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LmsMemberRepository extends JpaRepository<LmsUser, Long> {
    boolean existsByPmUserId(String email);
    Optional<LmsUser> findByPmUserId(String userId);
}