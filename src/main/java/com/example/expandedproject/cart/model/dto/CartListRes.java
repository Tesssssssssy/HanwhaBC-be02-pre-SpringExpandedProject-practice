package com.example.expandedproject.cart.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartListRes {
    Boolean isSuccess;
    Integer code;
    String message;
    List<CartDto> result;
    Boolean success;
}
