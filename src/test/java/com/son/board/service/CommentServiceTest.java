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
import java.util.List;

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
                .build();

        userRepository.save(user);
        postService.savePost(postRequestDto, user.getId());
    }

    /* 댓글 등록 테스트 */
    @Test
    public void saveTest() {
        // given
        CommentRequestDto comment = CommentRequestDto.builder()
                .content("댓글1")
                .path("")
                .createdDate(LocalDateTime.now())
                .build();

        // when
        commentService.saveComment(comment, 1, 1);

        // then
        PostResponseDto targetPost = postService.findPost(1);
        Comment targetComment = targetPost.getComments().get(0);
        Assertions.assertThat(targetComment.getContent()).isEqualTo(comment.getContent());
    }

    /* 댓글 삭제 테스트 */
    @Test
    public void deleteTest() {
        // given
        CommentRequestDto comment1 = CommentRequestDto.builder()
                .content("댓글1")
                .path("")
                .createdDate(LocalDateTime.now())
                .build();

        CommentRequestDto comment2 = CommentRequestDto.builder()
                .content("댓글2")
                .path("")
                .createdDate(LocalDateTime.now())
                .build();

        commentService.saveComment(comment1, 1, 1);
        commentService.saveComment(comment2, 1, 1);

        // when
        commentService.deleteComment(1, 1);
        entityManager.flush();
        entityManager.clear();

        // then
        PostResponseDto targetPost = postService.findPost(1);
        List<Comment> comments = targetPost.getComments();
        Assertions.assertThat(comments.size()).isEqualTo(1);
    }
}
