package com.son.board.service;

import com.son.board.domain.Comment;
import com.son.board.domain.User;
import com.son.board.dto.CommentRequestDto;
import com.son.board.dto.PostRequestDto;
import com.son.board.dto.PostResponseDto;
import com.son.board.repository.CommentRepository;
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
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

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
        commentRepository.deleteAll();
        System.out.println("DB 초기화 완료");

        entityManager.createNativeQuery("ALTER TABLE post AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE comment AUTO_INCREMENT = 1").executeUpdate();
        System.out.println("auto_increment 초기화 완료");

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
    }

    @Test
    public void saveTest() {
        // given
        CommentRequestDto comment = CommentRequestDto.builder()
                .content("댓글1")
                .path("")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        // when
        commentService.saveComment(comment, 1, 1);

        // then
        PostResponseDto targetPost = postService.findPost(1);
        Comment targetComment = targetPost.getComments().get(0);
        Assertions.assertThat(targetComment.getContent()).isEqualTo(comment.getContent());
    }
}
