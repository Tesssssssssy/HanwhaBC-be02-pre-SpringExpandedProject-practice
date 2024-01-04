package com.example.expandedproject.member.model.response;

import com.example.expandedproject.member.model.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSignUpMemberDtoRes {
    private Long id;
    private String email;
    private String nickname;
    private String token;
    private String jwt;
}
