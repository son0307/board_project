package com.son.board.controller;

import com.son.board.dto.PostLikeRequestDto;
import com.son.board.service.PostLikeService;
import com.son.board.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/post/toggle/{postId}")
    public ResponseEntity<?> toggleLike(@PathVariable int postId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostLikeRequestDto postLikeRequestDto = postLikeService.toggle(postId, userDetails.getId());

        return ResponseEntity.ok(postLikeRequestDto);
    }
}
