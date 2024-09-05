package com.pro.lms.service;

import com.pro.lms.entity.LmsUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {
    List<LmsUser> testGetApi() throws Exception;
    LmsUser testApiPost(LmsUser lmsUser) throws Exception;
}
