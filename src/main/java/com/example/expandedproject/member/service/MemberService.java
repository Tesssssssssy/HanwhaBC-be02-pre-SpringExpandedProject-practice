package com.example.expandedproject.member.service;

import com.example.expandedproject.exception.ErrorCode;
import com.example.expandedproject.exception.entityException.MemberException;
import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.member.model.request.PostMemberLoginReq;
import com.example.expandedproject.member.model.request.PostSignUpMemberReq;
import com.example.expandedproject.member.model.request.PutUpdateMemberReq;
import com.example.expandedproject.member.model.response.*;
import com.example.expandedproject.member.repository.MemberRepository;
import com.example.expandedproject.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    private final EmailVerifyService emailVerifyService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${message.email.from}")
    private String originEmail;

    // Member 일반 회원가입 메소드
    public PostSignUpMemberDtoRes signUpMember(PostSignUpMemberReq request) {
        if (!memberRepository.findByEmail(request.getEmail()).isPresent()) {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .authority("ROLE_USER")
                    .status(false)
                    // email 인증 과정을 거치지 않았기 때문에 default 값 false.
                    .build();

            member = memberRepository.save(member);
            // 입력받은 dto를 이용해 DB에 우선 저장.

            /*
            String token = UUID.randomUUID().toString();
            String jwt = JwtUtils.generateAccessToken(member.getUsername(), member.getNickname());

            token, jwt는 굳이 여기서 하지 말고 email 보낼 떄 생성해서 보내주는 것으로 한다.
            */

            sendEmail(member.getId(), member.getEmail(), member.getNickname());
            /*
                DB에 저장 후 member의 id, email, nickname을 param으로 해서
                이메일 인증 메소드 호출.
            */


            // email 인증 과정을 거쳐서 true가 되었을 때 최종 반환 response dto build.
            if (member.getStatus()) {
                MemberSignUpResult res = MemberSignUpResult.builder()
                        .idx(member.getId())
                        .status(member.getStatus())
                        // 해당 member의 status를 받아서 반환.
                        // email 관련 인증을 마쳤다면 true일 것.
                        .build();

                PostSignUpMemberDtoRes response = PostSignUpMemberDtoRes.builder()
                        .code(1000)
                        .message("요청 성공.")
                        .success(true)
                        .isSuccess(true)
                        .result(res)
                        .build();

                return response;
            }
            throw new MemberException(ErrorCode.UNAUTHORIZED_EMAIL);
            // email 인증을 하지 않았을 때 예외 처리.
        }
        throw new MemberException(ErrorCode.DUPLICATED_MEMBER);
        // 회원 가입 시 입력 받은 email로 회원 가입한 member가 이미 db에 존재할 때 예외 처리.
    }


    // 이메일 인증 로직을 따로 메소드로 분리
    public void sendEmail(Long id, String email, String nickname) {
        String token = UUID.randomUUID().toString();
        // Spring에서 제공하는 UUID 라이브러리 호출해 random token 생성.

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(originEmail);
        message.setTo(email);
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setSubject("[LTW] 이메일 인증");
        message.setText("http://localhost:8080/member/confirm?email="
                + email
                + "&token=" + token
                + "&jwt=" + JwtUtils.generateAccessToken(email, nickname, id, secretKey)
        );
        emailSender.send(message);
        // message 생성 후 emailSender를 이용해 email 인증을 위한 email 전송.

        emailVerifyService.create(email, token);
    }


    // Member 판매자 회원가입 메소드
    public PostSignUpMemberDtoRes signUpSeller(PostSignUpMemberReq request) {
        if (!memberRepository.findByEmail(request.getEmail()).isPresent()) {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .authority("ROLE_SELLER")
                    // 판매자 회원 가입이므로 default 권한 -> "ROLE_SELLER"
                    // 추후 Product 등록 시 ROLE_SELLER 권한을 가진 member만 접근 및 product 등록 가능.
                    .status(false)
                    .build();

            member = memberRepository.save(member);

            if (member.getStatus()) {
                MemberSignUpResult res = MemberSignUpResult.builder()
                        .idx(member.getId())
                        .status(member.getStatus())
                        .build();

                PostSignUpMemberDtoRes response = PostSignUpMemberDtoRes.builder()
                        .code(1000)
                        .message("요청 성공.")
                        .success(true)
                        .isSuccess(true)
                        .result(res)
                        .build();

                return response;
            }
            throw new MemberException(ErrorCode.UNAUTHORIZED_EMAIL);
        }
        throw new MemberException(ErrorCode.DUPLICATED_MEMBER);
    }


    // Member Login 메소드
    public PostMemberLoginRes login(PostMemberLoginReq req) {
        /*
            client로부터 request가 왔을 때 Authentication Filter가 이 요청을 가로채고
            UsernamePasswordAuthenticationToken을 통해 Authentication을 만들어
            Authentication Manager에게 authentication을 전달한다.
            그리고 AuthenticationManager는 AuthenticationProvider에게
            인증 관련 처리를 위임한다.
            그리고 AuthenticationProvider는 UserDetailsService를 통해
            DB에서 해당하는 유저가 있는지 찾고 비밀번호 일치 여부 등을 확인 후
            다시 Authentication을 만들어 AuthenticationManager에게 authentication을 전달한다.
            그리고 AuthenticationManager는 이 authentication을 AuthenticationFilter에게
            authentication을 전달하고
            AuthenticationFilter는 SecurityContextHoler에게 이를 전달하고
            SecurityContextHoler의 SecurityContext 안에 authentication을 저장한다.
        */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));


        if (authentication.isAuthenticated()) {
            /*
                authentication 인증 과정을 거쳐 최종적으로 인증이 되면
                보호된 authentication resource에 접근할 수 있는 principal을 이용해
                member의 id, email, nickname을 받아 오고
                받아온 id, email, nickname을 이용해 jwt token 생성.
            */
            Long id = ((Member) authentication.getPrincipal()).getId();
            String email = ((Member) authentication.getPrincipal()).getEmail();
            String nickname = ((Member) authentication.getPrincipal()).getNickname();

            String jwt = JwtUtils.generateAccessToken(email, nickname, id, secretKey);

            PostMemberLoginRes loginRes = PostMemberLoginRes.builder()
                    .token(jwt)
                    .build();

            return loginRes;
        }
        throw new MemberException(ErrorCode.AUTHENTICATION_FAIL);


        /*
        Member m = (Member) userDetailsService.loadUserByUsername(postMemberLoginReq.getUsername());

        if (!passwordEncoder.matches(postMemberLoginReq.getPassword(), m.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return JwtUtils.generateAccessToken(m.getEmail(), m.getNickname());
        */
    }


    // 이메일 인증을 한 유저의 status를 변경하기 위한 메소드
    public void updateMemberStatus(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            memberRepository.save(member);
        }
        throw new MemberException(ErrorCode.MEMBER_EMPTY);
    }


    // 회원가입 시 이메일 중복 확인
    public Boolean checkEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
