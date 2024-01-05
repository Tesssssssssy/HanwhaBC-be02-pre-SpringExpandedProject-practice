package com.example.expandedproject.product.model.dto.response;

import lombok.*;

@Builder
@Data
public class GetFindProductRes {
    private Long idx;
    private String name;
    private Long brandIdx;
    private Integer categoryIdx;
    private Integer price;
    private Integer salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String contents;
    private String filename;
    private Boolean like_check;
}
