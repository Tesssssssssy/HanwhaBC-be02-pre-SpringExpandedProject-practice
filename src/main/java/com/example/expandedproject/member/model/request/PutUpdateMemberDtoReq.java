package com.example.expandedproject.member.model.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PutUpdateMemberDtoReq {

    @NotNull
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내의 암호")
    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @NotNull
    @Pattern(regexp = "[가-힣0-9]{2,5}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$")
    @ApiParam(value = "닉네임", required = true, example = "campingOnTop")
    private String nickname;
}
