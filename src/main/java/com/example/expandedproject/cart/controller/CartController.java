package com.example.expandedproject.cart.controller;

import com.example.expandedproject.cart.model.dto.request.PostCreateCartDtoReq;
import com.example.expandedproject.cart.service.CartService;
import com.example.expandedproject.member.model.response.GetMemberWithCartDtoRes;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name="HouseLike", description = "HouseLike 숙소 좋아요 CRUD")
@Api(tags = "HouseLike")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<String> createCart(@Valid @RequestBody PostCreateCartDtoReq request) {
        cartService.createCart(request);
        return ResponseEntity.ok().body("Cart create success");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GetMemberWithCartDtoRes>> findCartByUserId(@Valid @PathVariable Long userId) {
        List<GetMemberWithCartDtoRes> likedHouses = cartService.findCartByMemberId(userId);
        return ResponseEntity.ok().body(likedHouses);
    }
}
