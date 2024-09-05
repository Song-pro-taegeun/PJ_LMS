package com.pro.lms.auth.dto;

import com.pro.lms.auth.entity.Authority;
import com.pro.lms.entity.LmsUser;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

//@ApiModel("유저 정보")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;
    private String userName;
    private String pmPhone;
    private Long pmRegDt;
    private String userRole;

    public LmsUser toMember(PasswordEncoder passwordEncoder) {
        return LmsUser.builder()
                .pmUserId(email)
                .pmPwd(passwordEncoder.encode(password))
                .pmName(userName)
                .pmPhone(pmPhone)
                .authority(Authority.ROLE_USER)
                .userRole(userRole)
                .build();
    }

    // 아이디와 비번이 일치하는지 검증하는 로직
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
