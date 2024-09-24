package com.pro.lms.controller;

import com.pro.lms.dto.BoardMemberDto;
import com.pro.lms.entity.LmsUser;
import com.pro.lms.entity.Post;
import com.pro.lms.entity.User;
import com.pro.lms.service.TestService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = { "TEST 컨트롤러" })
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation("TEST API")
    @GetMapping("/testGet")
    public List<LmsUser> testApi()throws Exception{
        return testService.testGetApi();
    }

    @ApiOperation("Test Post Api")
    @PostMapping("/testPost")
    public LmsUser testApiPost(@RequestBody LmsUser lmsUser)throws Exception{
        return testService.testApiPost(lmsUser);
    }

    @ApiOperation("TEST JPA JOIN API")
    @GetMapping("/testJPAjoin/{username}")
    public List<User> testApi(@PathVariable("username") String username){
        return testService.getJoinJpaTest(username);
    }

//    @ApiOperation("TEST JPA JOIN Post API")
//    @GetMapping("/testJpaPostJoin/{username}")
//    public List<Post> getJoinJpaTestPost(@PathVariable("username") long username){
//        return testService.getJoinJpaTestPost(username);
//    }

    @PostMapping("/testInsert")
    public Post insertPost(@RequestBody Post post){
        return testService.insertPost(post);
    }

    @ApiOperation("TEST queryDslTest")
    @GetMapping("/queryDslTest/{userId}")
    public List<Post> queryDslTest(@PathVariable("userId") long userId){
        return testService.queryDslTest(userId);
    }


    @ApiOperation("TEST queryDslJoinTest")
    @GetMapping("/queryDslJoinTest/{userId}")
    public List<BoardMemberDto> queryDslJoinTest(@PathVariable("userId") long userId){
        return testService.queryDslJoinTest(userId);
    }
}
