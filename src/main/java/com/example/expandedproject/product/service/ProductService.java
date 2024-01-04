package com.example.expandedproject.product.service;

import com.example.expandedproject.aws.S3Service;
import com.example.expandedproject.exception.ErrorCode;
import com.example.expandedproject.exception.entityException.ProductException;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.model.request.PostCreateProductDtoReq;
import com.example.expandedproject.product.model.request.PostSetProductImgDtoReq;
import com.example.expandedproject.product.model.request.PutUpdateProductDtoReq;
import com.example.expandedproject.product.model.response.GetFindProductDtoRes;
import com.example.expandedproject.product.model.response.PostCreateProductDtoRes;
import com.example.expandedproject.product.model.response.PostSetProductImgDtoRes;
import com.example.expandedproject.product.model.response.PutUpdateProductDtoRes;
import com.example.expandedproject.product.repository.ProductRepository;
import com.example.expandedproject.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final S3Service s3Service;

    public PostCreateProductDtoRes createProduct(PostCreateProductDtoReq request) {
        Optional<Product> result = productRepository.findByName(request.getName());
        if (result.isPresent()) {
            throw new ProductException(ErrorCode.DUPLICATED_PNAME, String.format("상품 이름: %s", request.getName()));
        }

        Product product = Product.toEntity(request);
        product = productRepository.save(product);

        PostCreateProductDtoRes response = PostCreateProductDtoRes.toDto(product);

        return response;
    }

    public GetFindProductDtoRes findProductById(Long ProductId) {
        Optional<Product> result = productRepository.findById(ProductId);
        if (result.isPresent()) {
            Product Product = result.get();
            GetFindProductDtoRes res = GetFindProductDtoRes.toDto(Product);
            return res;
        }
        return null;
    }

    public List<GetFindProductDtoRes> findProductList() {
        List<Product> Products = productRepository.findAll();
        List<GetFindProductDtoRes> ProductList = new ArrayList<>();

        for (Product Product : Products) {
            GetFindProductDtoRes res = GetFindProductDtoRes.toDto(Product);
            ProductList.add(res);
        }
        return ProductList;
    }

    public PutUpdateProductDtoRes updateProduct(PutUpdateProductDtoReq req, Long ProductId) {
        Optional<Product> result = productRepository.findById(ProductId);
        if (result.isPresent()) {
            Product Product = result.get();

            Product.setName(req.getName());
            Product.setContent(req.getContent());
            Product.setPrice(req.getPrice());
            Product.setAddress(req.getAddress());
            Product.setLatitude(req.getLatitude());
            Product.setLongitude(req.getLongitude());
            Product.setMaxUser(req.getMaxUser());
            Product.setIsActive(req.getIsActive());
            Product.setHasAirConditioner(req.getHasAirConditioner());
            Product.setHasWashingMachine(req.getHasWashingMachine());

            Product = productRepository.save(Product);

            PutUpdateProductDtoRes res = PutUpdateProductDtoRes.toDto(Product);
            return res;
        }
        return null;
    }

    public PostSetProductImgDtoRes setProductImg(PostSetProductImgDtoReq request, Long ProductId) {
        Optional<Product> result = productRepository.findById(ProductId);
        if (result.isPresent()) {
            Product Product = result.get();

            if (request.getImg() != null) {
                String savePath = ImageUtils.makeProductImagePath(request.getImg().getOriginalFilename());
                savePath = s3Service.uploadFile(request.getImg(), savePath);
                Product.setImg(savePath);
            } else {
                throw new ProductException(ErrorCode.IMAGE_EMPTY);
            }
            Product = productRepository.save(Product);
            return PostSetProductImgDtoRes.toDto(Product);
        }
        return null;
    }

    public void deleteProduct(Long ProductId) {
        productRepository.delete(Product.builder().id(ProductId).build());
    }
}
