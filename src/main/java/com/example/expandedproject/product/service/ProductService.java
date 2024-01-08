package com.example.expandedproject.product.service;

import com.example.expandedproject.exception.ErrorCode;
import com.example.expandedproject.exception.entityException.ProductException;
import com.example.expandedproject.image.model.Image;
import com.example.expandedproject.image.service.ImageService;
import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.model.dto.request.PostProductReq;
import com.example.expandedproject.product.model.dto.request.PatchProductUpdateReq;
import com.example.expandedproject.product.model.dto.response.*;
import com.example.expandedproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    @Transactional
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
                .likeCount(0)
                .build();

        product = productRepository.save(product);

        imageService.createImage(product.getId(), uploadFiles);
        // 이미지 관련 로직은 ImageService에 구현되어 있으므로 imageService의 createImage 메소드 호출.

        PostProductCreateRes response = PostProductCreateRes.builder()
                .code(1000)
                .message("요청 성공")
                .success(true)
                .isSuccess(true)
                .result(ProductCreateResult.builder().idx(product.getId()).build())
                .build();

        return response;
    }


    /**
     *  Paging을 이용한 Product List 메소드
     */
    public ProductListRes findProductListPaging(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        // 보통 1 페이지부터 시작하므로 1 빼고 시작한다.

        Page<Product> result = productRepository.findListUsingPaging(pageable);

        List<GetFindProductRes> productReadResList = new ArrayList<>();
        for (Product product : result) {
            List<Image> images = product.getImageList();

            String filenames = "";
            for (Image image : images) {
                String filename = image.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);
            // Image의 filename은 AWS S3에 저장된 경로가 저장되어 있기 때문에
            // 여러 이미지의 경우 filename들을 parsing하기 위한 작업.
            // 프론트엔드에 사진을 띄워주기 위한 작업. (프론트엔드 코드 이상함으로 인한 불편한 코드)

            GetFindProductRes productReadRes = GetFindProductRes.builder()
                    .idx(product.getId())
                    .name(product.getName())
                    .contents(product.getContents())
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


    public ProductListRes findProductList() {
        List<Product> result = productRepository.findList();

        List<GetFindProductRes> productReadResList = new ArrayList<>();
        for (Product product : result) {
            List<Image> images = product.getImageList();

            String filenames = "";
            for (Image image : images) {
                String filename = image.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);
            // Image의 filename은 AWS S3에 저장된 경로가 저장되어 있기 때문에
            // 여러 이미지의 경우 filename들을 parsing하기 위한 작업.
            // 프론트엔드에 사진을 띄워주기 위한 작업. (프론트엔드 코드 이상함으로 인한 불편한 코드)

            GetFindProductRes productReadRes = GetFindProductRes.builder()
                    .idx(product.getId())
                    .name(product.getName())
                    .contents(product.getContents())
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
                    .contents(product.getContents())
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
        throw new ProductException(ErrorCode.PRODUCT_EMPTY);
    }

    public void update(PatchProductUpdateReq req) {
        Optional<Product> result = productRepository.findById(req.getId());
        if (result.isPresent()) {
            Product product = result.get();
            product.setName(req.getName());
            product.setPrice(req.getPrice());

            productRepository.save(product);
        }
        throw new ProductException(ErrorCode.PRODUCT_EMPTY);
    }

    public void delete(Long id) {
        productRepository.delete(Product.builder().id(id).build());
    }

}
