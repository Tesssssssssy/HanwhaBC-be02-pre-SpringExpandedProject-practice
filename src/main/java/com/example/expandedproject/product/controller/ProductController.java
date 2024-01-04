package com.example.expandedproject.product.controller;

import com.example.expandedproject.product.model.request.PostCreateProductDtoReq;
import com.example.expandedproject.product.model.request.PostSetProductImgDtoReq;
import com.example.expandedproject.product.model.request.PutUpdateProductDtoReq;
import com.example.expandedproject.product.model.response.GetFindProductDtoRes;
import com.example.expandedproject.product.model.response.PostCreateProductDtoRes;
import com.example.expandedproject.product.model.response.PostSetProductImgDtoRes;
import com.example.expandedproject.product.model.response.PutUpdateProductDtoRes;
import com.example.expandedproject.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name="Product", description = "Product 숙소 CRUD")
@Api(tags = "Product")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Product")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProductService productService;


    @Operation(summary = "Product 숙소 등록",
            description = "숙소 등록를 등록하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/create")
    public ResponseEntity createProduct(@Valid @RequestBody PostCreateProductDtoReq postCreateProductDtoReq) {
        PostCreateProductDtoRes response = productService.createProduct(postCreateProductDtoReq);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Product 숙소 조회",
            description = "숙소 ID로 숙소 1개를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/find/{ProductId}")
    public ResponseEntity findProductById(@Parameter(description = "조회할 Product의 id") @PathVariable Long ProductId) {
        log.debug("[Product] ProductId: {}", ProductId);
        GetFindProductDtoRes response = productService.findProductById(ProductId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Product 숙소 목록 조회",
            description = "전체 숙소들의 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/findList")
    public ResponseEntity findProductList() {
        List<GetFindProductDtoRes> ProductList = productService.findProductList();
        return ResponseEntity.ok().body(ProductList);
    }

    @Operation(summary = "Product 숙소 정보 수정",
            description = "숙소의 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PutMapping("/update/{ProductId}")
    public ResponseEntity updateProduct(@Valid @RequestBody PutUpdateProductDtoReq putUpdateProductDtoReq, @PathVariable Long ProductId) {
        PutUpdateProductDtoRes Product = productService.updateProduct(putUpdateProductDtoReq, ProductId);
        return ResponseEntity.ok().body(Product);
    }

    @Operation(summary = "Product 숙소 이미지 수정",
            description = "숙소의 이미지를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping(value = "/update/img/{ProductId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity setProductImg(@Valid PostSetProductImgDtoReq request, @PathVariable Long ProductId) {
        PostSetProductImgDtoRes Product = productService.setProductImg(request, ProductId);
        return ResponseEntity.ok().body(Product);
    }

    @Operation(summary = "Product 숙소 삭제",
            description = "숙소 ID로 숙소 데이터 1개를 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @DeleteMapping("/delete/{ProductId}")
    public ResponseEntity deleteProduct(@Parameter(description = "삭제할 Product의 id") @PathVariable Long ProductId) {
        productService.deleteProduct(ProductId);
        return ResponseEntity.ok().body("Product delete success");
    }
}
