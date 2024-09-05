package com.pro.lms.config;

import com.pro.lms.auth.jwt.JwtAccessDeniedHandler;
import com.pro.lms.auth.jwt.JwtAuthenticationEntryPoint;
import com.pro.lms.auth.jwt.JwtPasswordEncoder;
import com.pro.lms.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

//참고 : https://velog.io/@juno0713/Spring-Security-JWT-React-w3wpg5yi
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger/**",
            /* swagger v3 */
            "/v3/api-docs/**", "/swagger-ui/**"
            ,"/auth/**"
            ,"/test/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        //bcrypt -> sha-512
        //return new BCryptPasswordEncoder();
        return new JwtPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http에서 사용하기 위해
        http.httpBasic().disable().csrf().disable().sessionManagement()
                // http.httpBasic().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)
                .and().authorizeRequests().antMatchers(PERMIT_URL_ARRAY).permitAll().anyRequest()
                .authenticated().and().apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
