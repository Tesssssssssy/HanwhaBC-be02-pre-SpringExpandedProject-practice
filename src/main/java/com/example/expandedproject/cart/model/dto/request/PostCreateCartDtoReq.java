package com.example.expandedproject.cart.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateCartDtoReq {
    private Long memberId;
    private Long productId;
}
