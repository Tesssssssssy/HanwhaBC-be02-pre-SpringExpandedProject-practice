package com.example.expandedproject.member.model.request;

import lombok.*;

@Builder
@Data
public class GetEmailConfirmReq {
    private String email;
    private String token;
    private String jwt;
}
