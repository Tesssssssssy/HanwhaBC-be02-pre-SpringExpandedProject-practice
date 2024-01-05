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

    @RequestMapping(method = RequestMethod.POST, value = "/in")
    public ResponseEntity in(@AuthenticationPrincipal Member member, @RequestBody CartCreateReq cartCreateReq) {
        cartService.create(member, cartCreateReq);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@AuthenticationPrincipal Member member) {

        return ResponseEntity.ok().body(cartService.list(member));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cancel/{idx}")
    public ResponseEntity in(@AuthenticationPrincipal Member member,@PathVariable Long idx) {
        cartService.remove(member, idx);
        return ResponseEntity.ok().body("ok");
    }
}
