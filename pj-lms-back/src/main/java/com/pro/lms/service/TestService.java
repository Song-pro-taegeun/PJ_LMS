package com.pro.lms.service;

import com.pro.lms.entity.LmsUser;
import com.pro.lms.entity.Post;
import com.pro.lms.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {
    List<LmsUser> testGetApi() throws Exception;
    LmsUser testApiPost(LmsUser lmsUser) throws Exception;
    List<User> getJoinJpaTest(String username);
    List<Post> getJoinJpaTestPost(String username);
    Post insertPost(Post post);
}
