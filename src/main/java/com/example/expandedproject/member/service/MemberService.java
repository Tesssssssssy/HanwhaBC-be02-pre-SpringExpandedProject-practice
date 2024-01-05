package com.example.expandedproject.member.service;

import com.example.expandedproject.exception.ErrorCode;
import com.example.expandedproject.exception.entityException.MemberException;
import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.member.model.request.PostMemberLoginReq;
import com.example.expandedproject.member.model.request.PostSignUpMemberReq;
import com.example.expandedproject.member.model.request.PutUpdateMemberReq;
import com.example.expandedproject.member.model.response.GetFindMemberRes;
import com.example.expandedproject.member.model.response.MemberSignUpResult;
import com.example.expandedproject.member.model.response.PostSignUpMemberDtoRes;
import com.example.expandedproject.member.model.response.PutUpdateMemberDtoRes;
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

    public PostSignUpMemberDtoRes signUpMember(PostSignUpMemberReq request) {
        if (!memberRepository.findByEmail(request.getEmail()).isPresent()) {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .authority("ROLE_USER")
                    .status(false)
                    .build();

            member = memberRepository.save(member);

            /*
            String token = UUID.randomUUID().toString();
            String jwt = JwtUtils.generateAccessToken(member.getUsername(), member.getNickname());

            token, jwt는 굳이 여기서 하지 말고 email 보낼 떄 생성해서 보내주는 것으로 한다.
            */

            sendEmail(member.getId(), member.getEmail(), member.getNickname());

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
        }
        throw new MemberException(ErrorCode.DUPLICATED_MEMBER);
    }


    /**
     *  이메일 인증 로직을 따로 메소드로 분리
     */
    public void sendEmail(Long id, String email, String nickname) {
        String token = UUID.randomUUID().toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ewoo9762@gmail.com");
        message.setTo(email);
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setSubject("[심마켓] 이메일 인증");
        message.setText("http://localhost:8080/member/confirm?email="
                + email
                + "&token=" + token
                + "&jwt=" + JwtUtils.generateAccessToken(email, nickname, id, secretKey)
        );
        emailSender.send(message);

        emailVerifyService.create(email, token);
    }

    public PostSignUpMemberDtoRes signUpSeller(PostSignUpMemberReq request) {
        if (!memberRepository.findByEmail(request.getEmail()).isPresent()) {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .authority("ROLE_SELLER")
                    .status(false)
                    .build();

            member = memberRepository.save(member);

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
        }
        throw new MemberException(ErrorCode.DUPLICATED_MEMBER);
    }


    public Member getMemberByEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }


    public String login(PostMemberLoginReq req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        if (authentication.isAuthenticated()) {
            Long id = ((Member) authentication.getPrincipal()).getId();
            String nickname = ((Member) authentication.getPrincipal()).getNickname();
            return JwtUtils.generateAccessToken(req.getUsername(), nickname, id, secretKey);
        }
        return null;

        /*
        Member m = (Member) userDetailsService.loadUserByUsername(postMemberLoginReq.getUsername());

        if (!passwordEncoder.matches(postMemberLoginReq.getPassword(), m.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return JwtUtils.generateAccessToken(m.getEmail(), m.getNickname());
        */
    }


    /**
     *  이메일 인증을 한 유저의 status를 변경하기 위한 메소드
     */
    public void updateMemberStatus(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            memberRepository.save(member);
        }
    }


    public Boolean checkEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


// -----------------------------------------------------------------------------------------------------------------




    public GetFindMemberRes findMemberById(Long userId) {
        Optional<Member> result = memberRepository.findById(userId);
        if (result.isPresent()) {
            Member member = result.get();

            return GetFindMemberRes.toDto(member);
        }
        return null;
    }


    public List<GetFindMemberRes> findMemberList() {
        List<Member> members = memberRepository.findAll();
        List<GetFindMemberRes> userList = new ArrayList<>();

        for (Member member : members) {
            GetFindMemberRes res = GetFindMemberRes.toDto(member);
            userList.add(res);
        }
        return userList;
    }


    public PutUpdateMemberDtoRes updateMember(PutUpdateMemberReq req, Long userId) {
        Optional<Member> result = memberRepository.findById(userId);
        if (result.isPresent()) {
            Member member = result.get();

            member.setPassword(req.getPassword());
            member.setNickname(req.getNickname());

            member = memberRepository.save(member);

            PutUpdateMemberDtoRes res = PutUpdateMemberDtoRes.toDto(member);
            return res;
        }
        return null;
    }


    public void deleteMember(Long userId) {
        memberRepository.delete(Member.builder().id(userId).build());
    }


}
