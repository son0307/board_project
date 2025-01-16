package com.son.board.service;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import com.son.board.dto.PostRequestDto;
import com.son.board.repository.PostRepository;
import com.son.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    @AfterEach
    public void reset() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        // given
        User user = User.builder()
                .username("id")
                .password("1234")
                .nickname("닉네임")
                .register_date(LocalDateTime.now())
                .build();

        PostRequestDto postRequestDto = PostRequestDto.builder()
                            .title("제목1")
                            .content("내용1")
                            .createdDate(LocalDateTime.now())
                            .modifiedDate(LocalDateTime.now())
                            .build();

        userRepository.save(user);

        // when
        postService.save(postRequestDto, user.getId());

        // then
        Post savedPost = postRepository.findAll().get(0);
        Assertions.assertThat(savedPost.getTitle()).isEqualTo("제목1");
    }
}
