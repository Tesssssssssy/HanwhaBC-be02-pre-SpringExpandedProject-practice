package com.example.expandedproject.product.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductListRes {
    Boolean isSuccess;
    Integer code;
    String message;
    List<GetFindProductRes> result;
    Boolean success;
}
