package com.example.expandedproject.member.model.response;

import com.example.expandedproject.member.model.Member;
import lombok.*;

@Builder
@Data
public class GetFindMemberRes {
    private Long idx;
    private String email;
    private String nickname;

    public static GetFindMemberRes toDto(Member member) {
        return GetFindMemberRes.builder()
                .idx(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
