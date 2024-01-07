package com.example.expandedproject.cart.service;

import com.example.expandedproject.cart.model.Cart;
import com.example.expandedproject.cart.model.dto.CartCreateReq;
import com.example.expandedproject.cart.model.dto.CartDto;
import com.example.expandedproject.cart.model.dto.CartListRes;
import com.example.expandedproject.cart.repository.CartRepository;
import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void createCart(Member member, CartCreateReq cartCreateReq){
        cartRepository.save(Cart.builder()
                        .member(member)
                        .product(Product.builder().id(cartCreateReq.getProductIdx()).build())
                        .amount(cartCreateReq.getAmount())
                        .build());
    }

    public CartListRes findCartList(Member member){
        List<Cart> cartList = cartRepository.findAllByMember(member);

        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : cartList) {
            CartDto dto = CartDto.builder()
                    .idx(cart.getId())
                    .name(cart.getProduct().getName())
                    .isTodayDeal(cart.getProduct().getIsTodayDeal())
                    .salePrice(cart.getProduct().getSalePrice())
                    .amount(cart.getAmount())
                    .brandIdx(cart.getMember().getId())
                    .deliveryType(cart.getProduct().getDeliveryType())
                    .price(cart.getProduct().getPrice())
                    .filename(cart.getProduct().getImageList().get(0).getFilename())
                    .build();
            cartDtos.add(dto);
        }

        CartListRes response = CartListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(cartDtos)
                .build();
        return response;
    }

    @Transactional
    public void deleteCartById(Member member, Long idx){
        cartRepository.deleteByIdAndMember(idx, member);
    }
}
