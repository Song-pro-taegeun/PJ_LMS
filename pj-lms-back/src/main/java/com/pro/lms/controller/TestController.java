package com.pro.lms.controller;

import com.pro.lms.entity.LmsUser;
import com.pro.lms.service.TestService;
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
}
