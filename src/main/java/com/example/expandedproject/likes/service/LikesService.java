package com.example.expandedproject.likes.service;

import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final ProductRepository productRepository;

    @Transactional
    public Void likes(Member member, Long idx) {
        Optional<Product> result = productRepository.findById(idx);

        if (result.isPresent()) {
            Product product = result.get();

            product.increaseLikeCount();
            product = productRepository.save(product);
        }
        return null;
    }
}
