package com.example.expandedproject.product.controller;

import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.dto.request.PostProductReq;
import com.example.expandedproject.product.model.dto.response.PostProductCreateRes;
import com.example.expandedproject.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name="Product", description = "Product CRUD")
@Api(tags = "Product")
@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity createProduct(@AuthenticationPrincipal Member member, @RequestPart PostProductReq req, @RequestPart MultipartFile[] uploadFiles) {
        PostProductCreateRes response = productService.createProduct(member, req, uploadFiles);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity findProductList() {
        return ResponseEntity.ok().body(productService.findProductList());
    }
}
