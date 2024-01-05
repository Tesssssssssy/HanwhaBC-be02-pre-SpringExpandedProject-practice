package com.example.expandedproject.product.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetProductListRes {
    Boolean isSuccess;
    Integer code;
    String message;
    GetFindProductRes result;
    Boolean success;
}
