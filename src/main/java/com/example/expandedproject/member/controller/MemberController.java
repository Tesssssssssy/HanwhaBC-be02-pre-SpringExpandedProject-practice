package com.example.expandedproject.member.controller;

import com.example.expandedproject.member.model.request.PostMemberLoginReq;
import com.example.expandedproject.member.model.request.PostSignUpMemberReq;
import com.example.expandedproject.member.model.request.PutUpdateMemberReq;
import com.example.expandedproject.member.model.response.GetFindMemberRes;
import com.example.expandedproject.member.model.response.MemberSignUpResult;
import com.example.expandedproject.member.model.response.PostSignUpMemberDtoRes;
import com.example.expandedproject.member.model.response.PutUpdateMemberDtoRes;
import com.example.expandedproject.member.service.EmailVerifyService;
import com.example.expandedproject.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.*;

@Tag(name="Member", description = "Member CRUD")
@Api(tags = "Member")
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
    // 위 의존성 주입받았던 객체들은 모두 service 단으로 이동해서 구현되었음.


    /**
     *  일반 유저 회원 가입
     */
    @Operation(summary = "Member 일반 회원가입",
            description = "일반 회원가입을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/signup")
    public ResponseEntity signUpMember(@Valid @RequestBody PostSignUpMemberReq req) {
        PostSignUpMemberDtoRes response = memberService.signUpMember(req);
        return ResponseEntity.ok().body(response);

        /*
        PostSignUpMemberDtoRes response = memberService.signUpMember(req);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ewoo9762@gmail.com");
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
     */
    @Operation(summary = "Member Seller 회원가입",
            description = "판매자 회원가입을 하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/sellersignup")
    public ResponseEntity signUpSeller(@Valid @RequestBody PostSignUpMemberReq req) {
        PostSignUpMemberDtoRes response = memberService.signUpSeller(req);
        return ResponseEntity.ok().body(response);
    }


    /**
     *  로그인
     */
    @PostMapping( "/authenticate")
    public ResponseEntity login(@Valid @RequestBody PostMemberLoginReq postMemberLoginReq) {
        Map<String, String> map = new HashMap<>();
        map.put("token", memberService.login(postMemberLoginReq));

        return ResponseEntity.ok().body(map);
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



// ---------------------------------------------------------------------------------------------------------------------



    /**
     *  멤버 데이터 1개 조회
     * @param memberId
     */
    @Operation(summary = "Member 유저 조회",
            description = "member ID로 member 1명을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/find/{memberId}")
    public ResponseEntity findUserById(@Valid @Parameter(description = "조회할 user의 id") @PathVariable Long memberId) {
        log.debug("[member] memberId: {}", memberId);
        GetFindMemberRes response = memberService.findMemberById(memberId);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Member 목록 조회",
            description = "전체 유저들의 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/findList")
    public ResponseEntity findMemberList() {
        List<GetFindMemberRes> memberList = memberService.findMemberList();
        return ResponseEntity.ok().body(memberList);
    }


    @Operation(summary = "Member 정보 수정",
            description = "유저의 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PutMapping("/update/{memberId}")
    public ResponseEntity updateMember(@Valid @RequestBody PutUpdateMemberReq putUpdateMemberReq, @PathVariable Long memberId) {
        PutUpdateMemberDtoRes member = memberService.updateMember(putUpdateMemberReq, memberId);
        return ResponseEntity.ok().body(member);
    }


    @Operation(summary = "Member 삭제",
            description = "member ID로 멤버 데이터 1개를 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity deleteMember(@Valid @Parameter(description = "삭제할 member의 id") @PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().body("Member delete success");
    }


}
