package com.example.expandedproject.cart.controller;

import com.example.expandedproject.cart.model.dto.CartCreateReq;
import com.example.expandedproject.cart.service.CartService;
import com.example.expandedproject.member.model.Member;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="Cart", description = "Cart CRUD")
@Api(tags = "Cart")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @PostMapping("/in")
    public ResponseEntity createCart(@AuthenticationPrincipal Member member, @RequestBody CartCreateReq cartCreateReq) {
        cartService.createCart(member, cartCreateReq);
        return ResponseEntity.ok().body("create cart success");
    }

    @GetMapping("/list")
    public ResponseEntity findCartList(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(cartService.findCartList(member));
    }

    @GetMapping("/cancel/{idx}")
    public ResponseEntity deleteCartById(@AuthenticationPrincipal Member member,@PathVariable Long idx) {
        cartService.deleteCartById(member, idx);
        return ResponseEntity.ok().body("delete cart success");
    }
}
