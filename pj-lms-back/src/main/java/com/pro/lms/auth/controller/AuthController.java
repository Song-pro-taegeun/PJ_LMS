package com.pro.lms.auth.controller;

import com.pro.lms.LmsApplication;
import com.pro.lms.auth.dto.MemberRequestDto;
import com.pro.lms.auth.dto.MemberResponseDto;
import com.pro.lms.auth.dto.TokenDto;
import com.pro.lms.auth.dto.TokenRequestDto;
import com.pro.lms.auth.service.AuthService;
import com.pro.lms.repository.LmsMemberRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
@Api(tags = { "Auth" })
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Resource
    private LmsMemberRepository lmsMemberRepository;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto, @RequestHeader HttpHeaders header) {
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

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        String sessionId = LmsApplication.makeNextSessionId();
        return ResponseEntity.ok(authService.reissue(tokenRequestDto, sessionId));
    }
}