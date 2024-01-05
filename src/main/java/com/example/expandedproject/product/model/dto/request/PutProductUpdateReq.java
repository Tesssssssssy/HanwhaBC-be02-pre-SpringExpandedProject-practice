package com.example.expandedproject.product.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PutProductUpdateReq {
    Long id;
    String name;
    Integer price;
}
