package com.pro.lms.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {}

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Integer getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Integer.parseInt(authentication.getName());
    }

    public static String getJwt(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        //Header에서 Bearer 부분 이하로 붙은 token을 파싱한다.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    //클레임 파싱
    public static JsonElement parseClaims(String nonBearerToekn) {

        //클레임은 poyload부분만 있어도 되므로 굳이 키값이 필요 없음.
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String userDetailsBytesToStrings = new String(decoder.decode(nonBearerToekn.split("\\.")[1]));

        JsonParser jsonParser = new JsonParser();
        JsonElement element = JsonParser.parseString(userDetailsBytesToStrings);

        return element;
    }

    //파싱한 클레임에서 해당 키의 값만 가져옴.
    public static String getVarFromClaim(String nonBearerToekn, String key) {
        return parseClaims(nonBearerToekn).getAsJsonObject().get(key).getAsString();
    }
}
