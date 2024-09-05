package com.pro.lms.service;

import com.pro.lms.entity.LmsUser;
import com.pro.lms.entity.Post;
import com.pro.lms.entity.User;
import com.pro.lms.repository.LmsMemberRepository;
import com.pro.lms.repository.PostRepository;
import com.pro.lms.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

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

    public List<User> getJoinJpaTest(String username){
        return userRepository.findByUsername(username);
    }

    public List<Post> getJoinJpaTestPost(String username){
        return postRepository.findByUserUsername(username);
    }

    public Post insertPost(Post post){
        return postRepository.save(post);
    }
}
