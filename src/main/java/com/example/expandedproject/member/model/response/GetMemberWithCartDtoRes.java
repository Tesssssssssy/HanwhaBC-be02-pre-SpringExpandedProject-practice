package com.example.expandedproject.member.model.response;

import lombok.*;

import java.util.Date;

@Builder
@Data
public class GetMemberWithCartDtoRes {
    private Long idx;
    private String email;
    private String name;
    private String nickname;
    private String img;
    private Date createdAt;
    private Date updatedAt;
    // private List<GetFindProductDtoRes> likedProducts;
}
