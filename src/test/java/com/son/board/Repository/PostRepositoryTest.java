package com.son.board.Repository;

import com.son.board.domain.Post;
import com.son.board.domain.User;
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
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    @AfterEach
    public void reset() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveAndLoadTest() {
        // given
        User user = User.builder()
                .username("id")
                .password("1234")
                .nickname("닉네임")
                .register_date(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .user(user)
                .build();

        // when
        userRepository.save(user);
        postRepository.save(post);

        // then
        Post findPost = postRepository.findById(post.getId()).get();
        Assertions.assertThat(findPost.getId()).isEqualTo(post.getId());
    }
}
