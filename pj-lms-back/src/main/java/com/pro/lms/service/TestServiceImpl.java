package com.pro.lms.service;

import com.pro.lms.dto.BoardMemberDto;
import com.pro.lms.entity.LmsUser;
import com.pro.lms.entity.Post;
import com.pro.lms.entity.User;
import com.pro.lms.repository.LmsMemberRepository;
import com.pro.lms.repository.PostRepository;
import com.pro.lms.repository.UserRepository;
import com.pro.lms.util.AesCipher;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pro.lms.entity.QPost.post;
import static com.pro.lms.entity.QUser.user;

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

//    public List<Post> getJoinJpaTestPost(long username){
//        return postRepository.findByUserUsername(username);
//    }

    public Post insertPost(Post post){
        return postRepository.save(post);
    }

    public List<Post> queryDslTest(long userId){
        return postRepository.queryDslTest(userId);
    };

    public List<BoardMemberDto> queryDslJoinTest(long userId){
        return postRepository.queryDslJoinTest(userId);

        // dto에서 @QueryProjection를 작성하지 않았을 때 -> custom Repository 에서 DTO 매핑이되지 않아 튜플로 리턴값을 받은 후 직접 매핑해줘야한다.
        // 너무 귄찮으니 @QueryProjection를 작성을 권장
//        List<Tuple> tuples = postRepository.queryDslJoinTest(userId);
//        return tuples.stream()
//                .map(tuple -> {
//                    BoardMemberDto dto = new BoardMemberDto();
//                    dto.setId(tuple.get(post.id));
//                    dto.setUserid(tuple.get(post.userId));
//                    dto.setTitle(tuple.get(post.title));
//                    dto.setContents(tuple.get(post.content));
//                    dto.setEmail(tuple.get(user.email));
//                    dto.setUsername(tuple.get(user.username));
//                    return dto;
//                })
//                .collect(Collectors.toList());
    };
}
