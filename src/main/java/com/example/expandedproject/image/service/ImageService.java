package com.example.expandedproject.image.service;

import com.example.expandedproject.aws.S3Service;
import com.example.expandedproject.image.model.Image;
import com.example.expandedproject.image.repository.ImageRepository;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    public void createImage(Long id, MultipartFile[] images) {
        for (MultipartFile image : images) {
            String savePath = ImageUtils.makeProductImagePath(image.getOriginalFilename());
            savePath = s3Service.uploadFile(image, savePath);

            Image img = Image.builder()
                    .filename(savePath)
                    .product(Product.builder().id(id).build())
                    .build();

            imageRepository.save(img);
        }
    }
}
