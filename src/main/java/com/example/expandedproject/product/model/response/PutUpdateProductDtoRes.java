package com.example.expandedproject.product.model.response;

import com.example.expandedproject.product.model.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutUpdateProductDtoRes {
    private Long id;

    private String name;
    private String content;
    private Integer price;

    private String address;
    private String latitude;
    private String longitude;

    private Integer maxUser;

    private Boolean isActive;
    private Boolean hasAirConditioner;
    private Boolean hasWashingMachine;

    public static PutUpdateProductDtoRes toDto(Product Product) {
        return PutUpdateProductDtoRes.builder()
                .id(Product.getId())
                .name(Product.getName())
                .content(Product.getContent())
                .price(Product.getPrice())
                .address(Product.getAddress())
                .latitude(Product.getLatitude())
                .longitude(Product.getLongitude())
                .maxUser(Product.getMaxUser())
                .isActive(Product.getIsActive())
                .hasAirConditioner(Product.getHasAirConditioner())
                .hasWashingMachine(Product.getHasWashingMachine())
                .build();
    }
}
