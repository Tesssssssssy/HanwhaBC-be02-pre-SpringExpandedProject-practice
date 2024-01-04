package com.example.expandedproject.product.model.response;

import com.example.expandedproject.product.model.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFindProductDtoRes {
    private Long id;

    // private Integer user_id;

    private String name;
    private String content;
    private Integer price;
    private String img;

    private String address;
    private String latitude;
    private String longitude;

    private Integer maxUser;

    private Boolean hasAirConditioner;
    private Boolean hasWashingMachine;

    public static GetFindProductDtoRes toDto(Product Product) {
        return GetFindProductDtoRes.builder()
                .id(Product.getId())
                .name(Product.getName())
                .content(Product.getContent())
                .price(Product.getPrice())
                .img(Product.getImg())
                .address(Product.getAddress())
                .latitude(Product.getLatitude())
                .longitude(Product.getLongitude())
                .maxUser(Product.getMaxUser())
                .hasAirConditioner(Product.getHasAirConditioner())
                .hasWashingMachine(Product.getHasWashingMachine())
                .build();
    }
}
