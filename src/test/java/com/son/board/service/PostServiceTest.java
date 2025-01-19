package com.son.board.service;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import com.son.board.dto.PostRequestDto;
import com.son.board.dto.PostResponseDto;
import com.son.board.repository.PostRepository;
import com.son.board.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void reset() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        System.out.println("DB 초기화 완료");

        entityManager.createNativeQuery("ALTER TABLE post AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        System.out.println("auto_increment 초기화 완료");
    }

    /* 게시글 등록 작동 테스트 */
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
        postService.savePost(postRequestDto, user.getId());

        // then
        Post savedPost = postRepository.findAll().get(0);
        Assertions.assertThat(savedPost.getTitle()).isEqualTo("제목1");
    }

    /* 게시글 불러오기 작동 테스트 */
    @Test
    public void loadTest() {
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
        postService.savePost(postRequestDto, user.getId());

        // when
        PostResponseDto reqPost = postService.findPost(1);

        // then
        Assertions.assertThat(reqPost.getTitle()).isEqualTo(postRequestDto.getTitle());
    }

    /* 게시글 수정 테스트 */
    @Test
    public void updateTest() {
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
        postService.savePost(postRequestDto, user.getId());

        // when
        PostRequestDto updatedPostRequestDto = PostRequestDto.builder()
                .title("수정된 제목1")
                .content("수정된 내용1")
                .createdDate(postRequestDto.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();

        postService.updatePost(updatedPostRequestDto, 1);

        // then
        String updatedTitle = postService.findPost(1).getTitle();
        Assertions.assertThat(updatedTitle).isEqualTo(updatedPostRequestDto.getTitle());
    }

    /* 게시글 삭제 테스트 */
    @Test
    public void deleteTest() {
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
        postService.savePost(postRequestDto, user.getId());

        // when
        postService.deletePost(1);

        // then
        Assertions.assertThat(postRepository.findAll().size()).isEqualTo(0);
    }
}
