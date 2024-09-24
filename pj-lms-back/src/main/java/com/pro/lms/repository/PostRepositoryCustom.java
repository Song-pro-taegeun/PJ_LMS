package com.pro.lms.repository;

import com.pro.lms.dto.BoardMemberDto;
import com.querydsl.core.QueryResults;
import com.pro.lms.entity.Post;
import com.querydsl.core.Tuple;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> queryDslTest(long userId);
    List<BoardMemberDto> queryDslJoinTest(long userId);
}
