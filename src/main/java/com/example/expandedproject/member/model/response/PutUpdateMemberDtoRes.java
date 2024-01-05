package com.example.expandedproject.member.model.response;

import com.example.expandedproject.member.model.Member;
import lombok.*;

import java.util.Date;

@Builder
@Data
public class PutUpdateMemberDtoRes {
    private Long idx;
    private String email;
    private String nickname;

    public static PutUpdateMemberDtoRes toDto(Member member) {
        return PutUpdateMemberDtoRes.builder()
                .idx(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
