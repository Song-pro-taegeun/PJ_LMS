package com.pro.lms.auth.jwt;

import com.pro.lms.auth.dto.TokenDto;
import com.pro.lms.auth.entity.UserCustom;
import com.pro.lms.entity.LmsUser;
import com.pro.lms.repository.LmsMemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";

    private final Key key;

    public static long ACCESS_TOKEN_EXPIRE_TIME;
    public static long REFRESH_TOKEN_EXPIRE_TIME;
    @Autowired
    private LmsMemberRepository lmsMemberRepository;

    public TokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Long accessTokenExpireTime, @Value("${jwt.refresh}")Long refreshTokenExpireTime) {
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTokenExpireTime;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenExpireTime;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //유저 정보를 받아 Access Token 과 Refresh Token 을 생성
    public TokenDto generateTokenDto(Authentication authentication, String sessionId) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        LmsUser loginUser = lmsMemberRepository.findByPmUserId(authentication.getName()).get();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder().setSubject(authentication.getName()) // payload "sub":
                .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn) // payload "exp": 1516239022 (예시)
                .claim("pmUserNo", loginUser.getPmUserNo())
                .claim("pmUserId", loginUser.getPmUserId())
                .claim("pmName", loginUser.getPmName())
                .claim("pmPhone", loginUser.getPmPhone())
                .claim("userRole", loginUser.getUserRole())
                .claim("sessionId", sessionId)
                .signWith(key, SignatureAlgorithm.HS512) // header "alg": "HS512"
                .compact();


        // Refresh Token 생성
        String refreshToken = Jwts.builder().setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME * 1000))
                .signWith(key, SignatureAlgorithm.HS512).compact();

        return TokenDto.builder().grantType(BEARER_TYPE).accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime()).refreshToken(refreshToken).build();
    }

    //JWT 토큰을 복호화하여 토큰에 들어 있는 정보를 꺼냄
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new UserCustom(claims.getSubject(), "", false, false
                , false, false
                , authorities
                , (Integer) claims.get("pmUserNo")
                , String.valueOf(claims.get("pmUserId"))
                , String.valueOf(claims.get("pmName"))
                , String.valueOf(claims.get("pmPhone")));

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    //토큰 정보를 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
