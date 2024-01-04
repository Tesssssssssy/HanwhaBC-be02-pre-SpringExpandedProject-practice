package com.example.expandedproject.member.model.response;

import com.example.expandedproject.member.model.Member;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutUpdateMemberDtoRes {
    private Long id;
    private String email;
    private String nickname;

    public static PutUpdateMemberDtoRes toDto(Member member) {
        return PutUpdateMemberDtoRes.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
