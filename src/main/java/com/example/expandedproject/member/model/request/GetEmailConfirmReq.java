package com.example.expandedproject.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetEmailConfirmReq {
    private String email;
    private String token;
    private String jwt;
}
