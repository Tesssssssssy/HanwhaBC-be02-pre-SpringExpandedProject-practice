package com.example.expandedproject.member.model.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSignUpMemberDtoReq {

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @ApiParam(value = "이메일", required = true, example = "test01@naver.com")
    private String email;

    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내의 암호")
    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "2~20글자의 한글, 영어, 숫자로 이루어진 닉네임")
    @ApiParam(value = "닉네임", example = "nickname")
    private String nickname;
}
