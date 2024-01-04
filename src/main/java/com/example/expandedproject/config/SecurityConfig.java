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
    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    @Value("${jwt.secret-key}")
    private String secretKey;


    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    .httpBasic().disable()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/member/*").permitAll()
                    .antMatchers("/product/*").permitAll()
                    .antMatchers("/swagger-ui/*").permitAll()
                    .anyRequest().authenticated();


            httpSecurity.addFilterBefore(new JwtFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class);
            httpSecurity.formLogin().disable();

            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
