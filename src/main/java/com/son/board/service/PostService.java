package com.son.board.service;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import com.son.board.dto.PostRequestDto;
import com.son.board.repository.PostRepository;
import com.son.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /* 게시글 추가 */
    @Transactional
    public Integer save(PostRequestDto postRequestDto, int userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new IllegalStateException("사용자 id 정보가 없습니다.");
        }

        postRequestDto.setUser(user.get());
        Post newPost = postRequestDto.toPostEntity();
        postRepository.save(newPost);

        return newPost.getId();
    }

    /* 게시글 목록 조회 */
}
