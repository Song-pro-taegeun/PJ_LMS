package com.pro.lms.repository;

import com.pro.lms.entity.Post;
import com.querydsl.core.QueryResults;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}