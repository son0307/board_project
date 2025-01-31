package com.son.board.controller;

import com.son.board.dto.CommentRequestDto;
import com.son.board.service.CommentService;
import com.son.board.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{postId}/register")
    public ResponseEntity<?> commentRegister(@PathVariable String postId,
                                             @RequestBody CommentRequestDto commentRequestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 임시로 path 설정
        commentRequestDto.setPath("");
        commentService.saveComment(commentRequestDto, userDetails.getUser().getId() , Integer.parseInt(postId));

        return ResponseEntity.ok(Map.of("message", "댓글이 등록되었습니다."));
    }
}
