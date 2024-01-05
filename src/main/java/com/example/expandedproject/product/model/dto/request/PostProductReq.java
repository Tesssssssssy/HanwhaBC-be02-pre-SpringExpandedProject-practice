package com.example.expandedproject.product.model.dto.request;

import lombok.*;

@Builder
@Data
public class PostProductReq {
    private String name;
    private Integer categoryIdx;
    private Integer price;
    private Integer salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String contents;
}
