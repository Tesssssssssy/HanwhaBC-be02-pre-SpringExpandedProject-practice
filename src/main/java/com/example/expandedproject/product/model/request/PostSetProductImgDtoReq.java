package com.example.expandedproject.product.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSetProductImgDtoReq {
    @NotNull
    private MultipartFile img;
}
