package com.example.expandedproject.likes.controller;

import com.example.expandedproject.likes.service.LikesService;
import com.example.expandedproject.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/{idx}")
    public ResponseEntity likes(@AuthenticationPrincipal Member member, @PathVariable Long idx) {
        try {
            likesService.likes(member, idx);
        } catch (Exception e) {
            System.out.println("Concurrency error");
            // likeService.likes(member, idx);
            // -> 에러 발생하고 다시 실행시키면 PESSIMISTIC_WRITE처럼 속도 느려짐
        }
        return ResponseEntity.ok().body("product add like success");
    }
}
