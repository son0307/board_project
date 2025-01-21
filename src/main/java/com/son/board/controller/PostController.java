package com.son.board.controller;

import com.son.board.domain.Post;
import com.son.board.dto.PostRequestDto;
import com.son.board.dto.PostResponseDto;
import com.son.board.dto.UserRequestDto;
import com.son.board.service.PostService;
import com.son.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    /* 페이지 id를 기준으로 내림차순으로 정렬, 한 페이지당 게시글 개수 10개 */
    @GetMapping("/")
    public String list(Model model, @RequestParam(defaultValue = "1") int page,
                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        // Spring에서는 0부터 시작하므로 -1 처리
        Pageable modifiedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        Page<Post> posts = postService.getAllPosts(modifiedPageable);

        int startPage = Math.max(1, page - 2);
        int lastPage = Math.min(posts.getTotalPages(), page + 2);

        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("hasNext", posts.hasNext());
        model.addAttribute("startPage", startPage);
        model.addAttribute("lastPage",lastPage);

        return "post/index";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable int id, Model model) {
        PostResponseDto target = postService.findPost(id);

        model.addAttribute("post", target);

        return "post/detail";
    }

    @GetMapping("/addData")
    public void addData() {
        UserRequestDto user = UserRequestDto.builder()
                .username("ID")
                .nickname("닉네임")
                .password("1234")
                .register_date(LocalDateTime.now())
                .build();

        // userService.saveUser(user);

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
