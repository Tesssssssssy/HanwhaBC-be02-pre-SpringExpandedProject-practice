package com.example.expandedproject.member.model.request;

import lombok.*;

@Builder
@Data
public class PostMemberLoginReq {
    private String username;
    private String password;
}
