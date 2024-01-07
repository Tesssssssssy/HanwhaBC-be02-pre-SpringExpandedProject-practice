package com.example.expandedproject.member.controller;

import com.example.expandedproject.member.model.request.PostMemberLoginReq;
import com.example.expandedproject.member.model.request.PostSignUpMemberReq;
import com.example.expandedproject.member.model.request.PutUpdateMemberReq;
import com.example.expandedproject.member.model.response.*;
import com.example.expandedproject.member.service.EmailVerifyService;
import com.example.expandedproject.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;
    // private final JavaMailSender javaMailSender;
    // private final AuthenticationManager authenticationManager;
    // 위 의존성 주입받았던 객체들은 모두 service 단으로 이동해서 구현.


    /**
     *  일반 유저 회원 가입
     *  -> "ROLE_USER"로 default 권한 부여
     */
    @PostMapping("/signup")
    public ResponseEntity signUpMember(@RequestBody PostSignUpMemberReq req) {
        PostSignUpMemberDtoRes response = memberService.signUpMember(req);
        return ResponseEntity.ok().body(response);

        /*
        PostSignUpMemberDtoRes response = memberService.signUpMember(req);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("보내는 이메일 주소");
        message.setTo(req.getEmail());
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setSubject("[심마켓] 이메일 인증");
        message.setText("http://localhost:8080/member/confirm?email="+req.getEmail()+ "&token="+response.getToken() +"&jwt="+response.getJwt());
        javaMailSender.send(message);

        emailVerifyService.create(req.getEmail(), response.getToken());

             이메일 인증 과정은 service에 따로 메소드 만들자.
             그리고 jwt에서 generateAccessToken 메소드 실행시키자.
        */
    }


    /**
     *  판매자 회원 가입
     *  -> "ROLE_SELLER"로 default 권한 부여
     */
    @Operation(summary = "Member Seller 회원가입",
            description = "판매자 회원가입을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/sellersignup")
    public ResponseEntity signUpSeller(@RequestBody PostSignUpMemberReq req) {
        PostSignUpMemberDtoRes response = memberService.signUpSeller(req);
        return ResponseEntity.ok().body(response);
    }


    /**
     *  로그인
     */
    @PostMapping( "/authenticate")
    public ResponseEntity login(@RequestBody PostMemberLoginReq postMemberLoginReq) {
        return ResponseEntity.ok().body(memberService.login(postMemberLoginReq));
    }


    /**
     *  일반 회원 가입 멤버 이메일 인증
     */
    @GetMapping( "/confirm")
    public RedirectView confirm(String email, String token, String jwt) {
        if (emailVerifyService.verify(email, token)) {
            memberService.updateMemberStatus(email);
            return new RedirectView("http://localhost:3000/emailconfirm/" + jwt);
        } else {
            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }


    /**
     *  이메일 중복 확인
     */
    @GetMapping("/checkemail")
    public ResponseEntity checkEmail(String email) {
        Boolean res = memberService.checkEmail(email);
        return ResponseEntity.ok().body(res);
    }
}
