package com.example.expandedproject.member.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberSignUpResult {
    private Long idx;
    private Boolean status;
}
