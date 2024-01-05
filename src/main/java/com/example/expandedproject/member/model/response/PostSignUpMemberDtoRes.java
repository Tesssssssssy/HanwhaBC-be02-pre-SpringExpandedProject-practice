package com.example.expandedproject.member.model.response;

import lombok.*;

@Builder
@Data
public class PostSignUpMemberDtoRes {
    /*
    private Long idx;
    private String email;
    private String nickname;
    private String token;
    private String jwt;
    */
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private MemberSignUpResult result;
    private Boolean success;
}
