package com.pro.lms.service;

import com.pro.lms.entity.LmsUser;
import com.pro.lms.repository.LmsMemberRepository;
import com.pro.lms.util.AesCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService{
    @Autowired
    private LmsMemberRepository memberRepository;

    @Resource
    private AesCipher aesCipher;

    public List<LmsUser> testGetApi() throws Exception{
        return memberRepository.findAll();
    }

    public LmsUser testApiPost(LmsUser lmsUser) throws Exception{
        String decryptId = aesCipher.decrypt(lmsUser.getPmUserId());
        Optional<LmsUser> data = memberRepository.findByPmUserId(decryptId);
        LmsUser result;

        if (data.isPresent()) {
            result = data.get();
        } else {
            result = null;
        }
        return result;
    }
}
