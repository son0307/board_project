package com.son.board.controller;

import com.son.board.domain.Post;
import com.son.board.dto.PostRequestDto;
import com.son.board.dto.PostResponseDto;
import com.son.board.service.PostLikeService;
import com.son.board.service.PostService;
import com.son.board.service.UserDetailsImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    /*
        게시글 리스트 출력
        페이지 id를 기준으로 내림차순으로 정렬, 한 페이지당 게시글 개수 10개
    */
    @GetMapping("/")
    public String postList(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "") String keyword,
                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        // Spring에서는 0부터 시작하므로 -1 처리
        Pageable modifiedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        Page<Post> posts;

        if(keyword.isBlank())
            posts = postService.getAllPosts(modifiedPageable);
        else
            posts = postService.searchByTitle(keyword, modifiedPageable);

        int startPage = Math.max(1, page - 2);
        int lastPage = Math.max(1, Math.min(posts.getTotalPages(), page + 2));

        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("hasNext", posts.hasNext());
        model.addAttribute("startPage", startPage);
        model.addAttribute("lastPage",lastPage);

        return "post/index";
    }

    /* 게시글 상세정보 조회 */
    @GetMapping("/{id}")
    public String postView(@PathVariable int id, Model model,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           HttpSession session) {
        // 세션에서 조회 기록 확인
        String sessionKey = "viewed_post_" + id;

        // 조회한 기록이 없으면 조회수 증가
        if(session.getAttribute(sessionKey) == null) {
            postService.updateViewCount(id);
            session.setAttribute(sessionKey, true);
        }

        PostResponseDto target = postService.findPost(id);
        boolean isLiked = false;

        if(userDetails != null) {
            isLiked = postLikeService.isUserLikePost(id, userDetails.getId());
        }

        model.addAttribute("post", target);
        model.addAttribute("isLiked", isLiked);

        return "post/detail";
    }

    /* 글쓰기 페이지로 이동 */
    @GetMapping("/post/write")
    public String moveToWrite(@RequestParam(value = "postId", required = false) Integer postId,
                              Model model) {
        if(Objects.isNull(postId)) {
            model.addAttribute("post", new PostRequestDto());
        }
        else {
            model.addAttribute("post", postService.findPost(postId));
        }

        return "post/write";
    }

    /* 새로운 글 등록 */
    @PostMapping("/post/register")
    public ResponseEntity<?> postRegister(@RequestBody PostRequestDto postRequestDto,
                                          @RequestParam(value = "postId", required = false) Integer postId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(Objects.isNull(postId)) {
            postService.savePost(postRequestDto, userDetails.getUser().getId());
        }
        else {
            postService.updatePost(postRequestDto, postId);
        }

        return ResponseEntity.ok(Map.of("message", "게시글이 등록되었습니다."));
    }

    /* 게시글 삭제 */
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<?> postDelete(@PathVariable("postId") int postId) {
        postService.deletePost(postId);

        return ResponseEntity.ok(Map.of("message", "게시글이 삭제되었습니다."));
    }
}
