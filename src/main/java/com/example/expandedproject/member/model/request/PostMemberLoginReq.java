package com.example.expandedproject.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostMemberLoginReq {
    private String username;
    private String password;
}
