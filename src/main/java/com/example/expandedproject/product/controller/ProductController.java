package com.example.expandedproject.product.controller;

import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.dto.request.PatchProductUpdateReq;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity createProduct(@AuthenticationPrincipal Member member, @RequestPart PostProductReq postProductReq, @RequestPart MultipartFile[] uploadFiles) {
        /*
            이 코드는 좋지 않다. 상품 등록을 하기 위해선 member id가 필요한데
            프론트엔드에서 쉽게 받아올 수 있지만 프론트엔드 코드를 수정할 수 없는 상태이고
            @AuthenticationPrincipal annotation으로 Member 객체 자체를 param으로 했기 떄문이다.
        */
        PostProductCreateRes response = productService.createProduct(member, postProductReq, uploadFiles);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity findProductList() {
        return ResponseEntity.ok().body(productService.findProductList());
    }

    @GetMapping("/{idx}")
    public ResponseEntity findProductById(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.findProductById(idx));
    }

    @PatchMapping( "/update")
    public ResponseEntity update(PatchProductUpdateReq productUpdateReq) {
        productService.update(productUpdateReq);
        return ResponseEntity.ok().body("수정");
    }

    @DeleteMapping( "/delete")
    public ResponseEntity delete(Long id) {
        productService.delete(id);
        return ResponseEntity.ok().body("삭제");
    }
}
