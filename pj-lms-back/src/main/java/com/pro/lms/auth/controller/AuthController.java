package com.pro.lms.auth.controller;

import com.pro.lms.LmsApplication;
import com.pro.lms.auth.dto.MemberRequestDto;
import com.pro.lms.auth.dto.MemberResponseDto;
import com.pro.lms.auth.dto.TokenDto;
import com.pro.lms.auth.dto.TokenRequestDto;
import com.pro.lms.auth.service.AuthService;
import com.pro.lms.auth.service.MailService;
import com.pro.lms.entity.LmsUser;
import com.pro.lms.repository.LmsMemberRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = { "Auth" })
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;

    @Autowired
    private LmsMemberRepository lmsMemberRepository;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto)  throws Exception {
        TokenDto dto;
        // 세션ID를 토큰에 추가하기 위해 생성한다.
        String sessionId = LmsApplication.makeNextSessionId();
        try {
            dto = authService.login(memberRequestDto, sessionId);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "이메일 코드 전송")
    @GetMapping("/verification/{pmUserId}")
    public String authVerification (@PathVariable String pmUserId) {
        return mailService.mailSend(pmUserId);
    }

    @ApiOperation(value = "비밀번호 재설정")
    @PostMapping("/password/reset")
    public int passwordReset(@RequestBody LmsUser lmsUser) {
        return authService.passwordReset(lmsUser);
    }

    @ApiOperation(value = "리프레쉬토큰 검증 및 액세스토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        String sessionId = LmsApplication.makeNextSessionId();
        return ResponseEntity.ok(authService.reissue(tokenRequestDto, sessionId));
    }

    @GetMapping("/emailCheck/{email}")
    public String emailCheck(@PathVariable String email) {
        return mailService.emailCheck(email);
    }
}
