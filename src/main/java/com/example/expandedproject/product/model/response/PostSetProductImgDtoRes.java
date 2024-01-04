package com.example.expandedproject.product.model.response;

import com.example.expandedproject.product.model.Product;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSetProductImgDtoRes {
    private Long id;
    private String address;
    private String name;
    private String img;

    public static PostSetProductImgDtoRes toDto(Product Product){
        return PostSetProductImgDtoRes.builder()
                .id(Product.getId())
                .name(Product.getName())
                .address(Product.getAddress())
                .img(Product.getImg())
                .build();
    }
}
