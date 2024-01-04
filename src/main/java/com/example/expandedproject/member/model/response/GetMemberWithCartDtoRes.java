package com.example.expandedproject.member.model.response;

import com.example.expandedproject.product.model.response.GetFindProductDtoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetMemberWithCartDtoRes {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String img;
    private Date createdAt;
    private Date updatedAt;
    private List<GetFindProductDtoRes> likedProducts;
}
