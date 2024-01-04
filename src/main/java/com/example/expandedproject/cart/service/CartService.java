package com.example.expandedproject.cart.service;

import com.example.expandedproject.cart.model.Cart;
import com.example.expandedproject.cart.model.dto.request.PostCreateCartDtoReq;
import com.example.expandedproject.cart.repository.CartRepository;
import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.member.model.response.GetMemberWithCartDtoRes;
import com.example.expandedproject.member.repository.MemberRepository;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.model.response.GetFindProductDtoRes;
import com.example.expandedproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    public void createCart(PostCreateCartDtoReq req) {
        Member member = memberRepository.findById(req.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + req.getMemberId()));

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + req.getProductId()));

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .build();

        cartRepository.save(cart);
    }

    public List<GetMemberWithCartDtoRes> findCartByMemberId(Long MemberId) {
        List<Cart> carts = cartRepository.findByMemberId(MemberId);
        List<GetMemberWithCartDtoRes> result = new ArrayList<>();

        if (!carts.isEmpty()) {
            for (Cart cart : carts) {
                Member member = cart.getMember();
                Product product = cart.getProduct();

                GetFindProductDtoRes productDto = GetFindProductDtoRes.toDto(product);

                GetMemberWithCartDtoRes memberDto = GetMemberWithCartDtoRes.builder()
                        .id(cart.getId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .createdAt(cart.getCreatedAt())
                        .updatedAt(cart.getUpdatedAt())
                        .likedProducts(Collections.singletonList(productDto))
                        .build();

                result.add(memberDto);
            }
            return result;
        }
        return null;
    }
}
