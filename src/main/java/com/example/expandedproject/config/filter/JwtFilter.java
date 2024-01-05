package com.example.expandedproject.config.filter;

import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.member.service.MemberService;
import com.example.expandedproject.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    // private final MemberService memberService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Header의 AUTHORIZATION에 담겨져 온 Bearer token 값을 header 변수에 저장.
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        /*
            만약 header 변수에 저장한 토큰 값이 null이 아니고 "Bearer "로 시작하면
            저장한 token을 parsing (Bearer 제거) 후 token 변수에 저장

            아니면 다음 filterchain의 다음 filter로 위임.
        */

        String token;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.split(" ")[1];
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        /*
            입력받은 token과 미리 설정한 secretkey를 이용해
            JWT token에 넣은 email, memberId, memberNickname 값 추출 후 저장.
        */
        String email = JwtUtils.getMemberEmail(token, secretKey);
        Long memberId = JwtUtils.getMemberId(token, secretKey);
        String memberNickname = JwtUtils.getMemberNickname(token, secretKey);


        /*
            token이 만료되었는지 검사 후
            만약 만료되었으면 filterchain의 다음 filter에게 위임.
        */
        if (!JwtUtils.validate(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
            UsernamePasswordAuthenticationToken 클래스를 이용해
            Member를 담은 Authentication 생성
        */
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                Member.builder().idx(memberId).email(email).build(), null,
                null
        );

        /*
        <내가 잘못한 코드>

        Member member = memberService.getMemberByEmail(email);
        String memberUsername = member.getUsername();
        if (!JwtUtils.validate(token, memberUsername, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                member, null,
                member.getAuthorities()
        );
        */


        /*
            인증 과정이 모두 종료되었고 SecurityContextHolder에게 전해졌음.
            이제 SecurityContext에 앞서 생성한 Authentication 정보 저장.
            그리고 filterchain의 다음 filter에게 위임.
        */
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
