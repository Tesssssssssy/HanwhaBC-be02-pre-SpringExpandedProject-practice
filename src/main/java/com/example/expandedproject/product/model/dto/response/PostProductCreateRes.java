package com.example.expandedproject.product.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostProductCreateRes {
    Boolean isSuccess;
    Integer code;
    String message;
    ProductCreateResult result;
    Boolean success;
}
