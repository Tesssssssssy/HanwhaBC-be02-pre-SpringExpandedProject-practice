package com.example.expandedproject.config;

import com.example.expandedproject.config.filter.JwtFilter;
import com.example.expandedproject.member.service.MemberService;
import com.example.expandedproject.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    /*
        private final MemberService memberService;

        나는 이 SecurityConfig에서 MemberService의 의존성 주입을 했었는데
        결과적으로 MemberService에서도 SecurityConfig에서도 MemberService를 의존하고
        SecurityConfig에서도 MemberService를 의존하여
        cycle 문제가 발생했다.
    */

    @Value("${jwt.secret-key}")
    private String secretKey;

    /*
        SpringSecurity에서 가장 중요한 AuthenticationManager를 Bean에 등록한다.
        AuthenticationManager는 유저의 요청이 들어왔을 때
        이 요청을 가로채 AuthenticationProvider에게 인증 과정을 위임한다.
        그리고 인증, 인가 과정이 끝나고 Authentication 정보가 반환되면
        이 SecurityContextHolder에게 전달하는 역할을 한다.
    */
    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated();

            // http.addFilterBefore(new JwtFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
            /*
                내가 만든 JwtFilter를 UsernamePasswordAuthenticationFiter 앞애서 실행되도록 끼워넣는다.
                나는 JwtFilter의 매개변수에 의존성 주입 받은 MemberService를 넣었는데
                결과적으로 이 과정이 문제가 되었고 cycle이 발생하게 된 것이다.
            */


            http.formLogin().disable();
            // SpringSecurity에서 기본적으로 제공하는 login form을 사용하지 않겠다.

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            /*
               우리는 JWT token을 이용한 login 방식을 사용하고
               session login 방식을 사용하지 않기 때문에
               Session을 저장하지 않겠다는 STATELESS로 session 관련 설정 적용.
            */

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
