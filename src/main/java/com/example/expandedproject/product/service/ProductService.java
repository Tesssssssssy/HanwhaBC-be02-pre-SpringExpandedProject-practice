package com.example.expandedproject.product.service;

import com.example.expandedproject.image.model.Image;
import com.example.expandedproject.image.service.ImageService;
import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.model.dto.request.PostProductReq;
import com.example.expandedproject.product.model.dto.request.PatchProductUpdateReq;
import com.example.expandedproject.product.model.dto.response.*;
import com.example.expandedproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public PostProductCreateRes createProduct(Member member, PostProductReq req, MultipartFile[] uploadFiles) {
        Product product = Product.builder()
                .name(req.getName())
                .brandIdx(member)
                .categoryIdx(1)
                .price(req.getPrice())
                .salePrice(req.getSalePrice())
                .deliveryType("무료")
                .isTodayDeal("n")
                .contents(req.getContents())
                .build();

        product = productRepository.save(product);

        imageService.createImage(product.getId(), uploadFiles);

        PostProductCreateRes response = PostProductCreateRes.builder()
                .code(1000)
                .message("요청 성공")
                .success(true)
                .isSuccess(true)
                .result(ProductCreateResult.builder().idx(product.getId()).build())
                .build();

        return response;
    }

    public ProductListRes findProductList() {
        List<Product> result = productRepository.findAll();

        List<GetFindProductRes> productReadResList = new ArrayList<>();
        for (Product product : result) {
            List<Image> images = product.getImageList();

            String filenames = "";
            for (Image image : images) {
                String filename = image.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            GetFindProductRes productReadRes = GetFindProductRes.builder()
                    .idx(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .brandIdx(product.getBrandIdx().getId())
                    .categoryIdx(product.getCategoryIdx())
                    .deliveryType(product.getDeliveryType())
                    .salePrice(product.getSalePrice())
                    .isTodayDeal(product.getIsTodayDeal())
                    .like_check(false)
                    .filename(filenames)
                    .build();
            productReadResList.add(productReadRes);
        }

        return ProductListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(productReadResList)
                .build();
    }

    public GetProductListRes findProductById(Long id) {
        Optional<Product> result = productRepository.findById(id);

        if (result.isPresent()) {
            Product product = result.get();

            List<Image> images = product.getImageList();

            String filenames = "";
            for (Image image : images) {
                String filename = image.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);

            GetFindProductRes getFindProductRes = GetFindProductRes.builder()
                    .idx(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .brandIdx(product.getBrandIdx().getId())
                    .categoryIdx(product.getCategoryIdx())
                    .deliveryType(product.getDeliveryType())
                    .salePrice(product.getSalePrice())
                    .isTodayDeal(product.getIsTodayDeal())
                    .like_check(false)
                    .filename(filenames)
                    .build();

            return GetProductListRes.builder()
                    .code(1000)
                    .message("요청 성공.")
                    .success(true)
                    .isSuccess(true)
                    .result(getFindProductRes)
                    .build();
        }
        return null;
    }

    public void update(PatchProductUpdateReq req) {
        Optional<Product> result = productRepository.findById(req.getId());
        if (result.isPresent()) {
            Product product = result.get();
            product.setName(req.getName());
            product.setPrice(req.getPrice());

            productRepository.save(product);
        }
    }

    public void delete(Long id) {
        productRepository.delete(Product.builder().id(id).build());
    }

}
