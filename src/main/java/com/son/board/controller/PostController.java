package com.son.board.controller;

import com.son.board.domain.Post;
import com.son.board.dto.PostRequestDto;
import com.son.board.dto.PostResponseDto;
import com.son.board.dto.UserRequestDto;
import com.son.board.service.PostService;
import com.son.board.service.UserDetailsImpl;
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

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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
    public String postView(@PathVariable int id, Model model) {
        postService.updateViewCount(id);
        PostResponseDto target = postService.findPost(id);

        model.addAttribute("post", target);

        return "post/detail";
    }

    /* 글쓰기 페이지로 이동 */
    @GetMapping("/write")
    public String moveToWrite() {
        return "post/write";
    }

    /* 새로운 글 등록 */
    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody PostRequestDto postRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 임시로 userId 설정하고 등록처리
        postService.savePost(postRequestDto, userDetails.getUser().getId());

        return ResponseEntity.ok(Map.of("message", "게시글이 등록되었습니다."));
    }

    @GetMapping("/addData")
    public void addData() {
        UserRequestDto user = UserRequestDto.builder()
                .username("ID")
                .nickname("닉네임")
                .password("1234")
                .register_date(LocalDateTime.now())
                .build();

        for(int i = 31; i < 60; i++) {
            PostRequestDto request = PostRequestDto.builder()
                    .content("내용" + i)
                    .title("테스트 데이터" + i)
                    .user(user.toUserEntity())
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            postService.savePost(request, 1);
        }
    }
}
