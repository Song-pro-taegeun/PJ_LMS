package com.pro.lms.service;

import com.pro.lms.dto.BoardMemberDto;
import com.pro.lms.entity.LmsUser;
import com.pro.lms.entity.Post;
import com.pro.lms.entity.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {
    List<LmsUser> testGetApi() throws Exception;
    LmsUser testApiPost(LmsUser lmsUser) throws Exception;
    List<User> getJoinJpaTest(String username);
//    List<Post> getJoinJpaTestPost(long username);
    Post insertPost(Post post);
    List<Post> queryDslTest(long userId);
    List<BoardMemberDto> queryDslJoinTest(long userId);
}
