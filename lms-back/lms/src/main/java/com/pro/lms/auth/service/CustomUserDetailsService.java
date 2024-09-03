package com.pro.lms.auth.service;

import com.pro.lms.auth.entity.UserCustom;
import com.pro.lms.entity.LmsUser;
import com.pro.lms.repository.LmsMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LmsMemberRepository memberRepository;
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 시도하려는 유저정보 조회
        LmsUser memberDTO = memberRepository.findByPmUserId(username).get();

        // 조회가 되지않는 고객은 에러발생.
        if (memberDTO == null) {
            throw new UsernameNotFoundException(username);
        }

        // 조회한 정보를 userCustom에 담는다.
        // 만약 파라미터를 추가해야한다면 UserCustom 을 먼저 수정한다.
//        UserCustom userCustom = new UserCustom(memberDTO.getEmail(), memberDTO.getPassword(),
//                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities(memberDTO),
//                memberDTO.getEmail());
        UserCustom userCustom = new UserCustom(memberDTO.getPmUserId(), memberDTO.getPmPwd(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities(memberDTO),
                memberDTO.getPmUserNo() ,memberDTO.getPmUserId(), memberDTO.getPmName(), memberDTO.getPmPhone()
        );


        return userCustom;
    }

    // DB에 등록된 권한에 따라 유저권한 부여 user_role
    private static Collection authorities(LmsUser memberDTO) {
        Collection authorities = new ArrayList<>();
//        // DB에 저장한 USER_ROLE 이 1이면 ADMIN 권한, 아니면 ROLE_USER 로 준다.
//        if (memberDTO.getUserTypeCd().equals("S")) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }
        return authorities;
    }
}
