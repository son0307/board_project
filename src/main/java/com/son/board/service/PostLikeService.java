package com.son.board.service;

import com.son.board.domain.Post;
import com.son.board.domain.PostLike;
import com.son.board.domain.User;
import com.son.board.dto.PostLikeResponseDto;
import com.son.board.repository.PostLikeRepository;
import com.son.board.repository.PostRepository;
import com.son.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public PostLikeResponseDto toggle(int postId, int userId) {
        PostLikeResponseDto postLikeResponseDto = new PostLikeResponseDto();

        // 게시글 조회
        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(postId + " : 게시글이 존재하지 않습니다."));

        // 사용자 조회
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자가 존재하지 않습니다."));

        // 좋아요 여부 확인
        Optional<PostLike> existingLikeOpt = postLikeRepository.findByPostAndUser(targetPost, targetUser);
        if(existingLikeOpt.isPresent()) {
            // 이미 좋아요가 있으면 삭제
            postLikeRepository.delete(existingLikeOpt.get());
            postLikeResponseDto.setLiked(false);
        } else {
            // 좋아요 추가
            PostLike postLike = PostLike.builder()
                    .post(targetPost)
                    .user(targetUser)
                    .build();
            postLikeRepository.save(postLike);
            postLikeResponseDto.setLiked(true);
        }

        // 좋아요 수 업데이트
        int favCount = postLikeRepository.countByPost(targetPost);
        targetPost.updateFavCount(favCount);
        postLikeResponseDto.setFavCount(favCount);

        return postLikeResponseDto;
    }

    public boolean isUserLikePost(int postId, int userId) {
        // 게시글 조회
        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(postId + " : 게시글이 존재하지 않습니다."));

        // 사용자 조회
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자가 존재하지 않습니다."));

        return postLikeRepository.existsByPostAndUser(targetPost, targetUser);
    }
}
