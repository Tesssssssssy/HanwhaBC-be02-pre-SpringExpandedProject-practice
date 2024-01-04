package com.example.expandedproject.member.service;

import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.member.model.request.PostMemberLoginReq;
import com.example.expandedproject.member.model.request.PostSignUpMemberDtoReq;
import com.example.expandedproject.member.model.request.PutUpdateMemberDtoReq;
import com.example.expandedproject.member.model.response.GetFindMemberDtoRes;
import com.example.expandedproject.member.model.response.PostSignUpMemberDtoRes;
import com.example.expandedproject.member.model.response.PutUpdateMemberDtoRes;
import com.example.expandedproject.member.repository.MemberRepository;
import com.example.expandedproject.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    public PostSignUpMemberDtoRes signUpMember(PostSignUpMemberDtoReq request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .authority("ROLE_USER")
                .isValid(false)
                .build();

        member = memberRepository.save(member);

        String token = UUID.randomUUID().toString();
        String jwt = JwtUtils.generateAccessToken(member.getUsername(), member.getAuthority(), member.getNickname());

        PostSignUpMemberDtoRes res = PostSignUpMemberDtoRes.builder()
                .id(member.getId())
                .email(member.getUsername())
                .nickname(request.getNickname())
                .token(token)
                .jwt(jwt)
                .build();

        return res;
    }


    public Member getMemberByEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }


    public String login(PostMemberLoginReq postMemberLoginReq) {

        // Optional<Member> result = memberRepository.findByEmail(postMemberLoginReq.getEmail());
        Member m = (Member) userDetailsService.loadUserByUsername(postMemberLoginReq.getUsername());

        if (!passwordEncoder.matches(postMemberLoginReq.getPassword(), m.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return JwtUtils.generateAccessToken(m.getEmail(), m.getAuthority(), m.getNickname());

    }


    public void updateMemberIsValid(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(result.isPresent()) {
            Member member = result.get();
            member.setIsValid(true);
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




    public GetFindMemberDtoRes findMemberById(Long userId) {
        Optional<Member> result = memberRepository.findById(userId);
        if (result.isPresent()) {
            Member member = result.get();

            return GetFindMemberDtoRes.toDto(member);
        }
        return null;
    }


    public List<GetFindMemberDtoRes> findMemberList() {
        List<Member> members = memberRepository.findAll();
        List<GetFindMemberDtoRes> userList = new ArrayList<>();

        for (Member member : members) {
            GetFindMemberDtoRes res = GetFindMemberDtoRes.toDto(member);
            userList.add(res);
        }
        return userList;
    }


    public PutUpdateMemberDtoRes updateMember(PutUpdateMemberDtoReq req, Long userId) {
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
