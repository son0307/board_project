package com.son.board.service;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import com.son.board.dto.PostRequestDto;
import com.son.board.dto.PostResponseDto;
import com.son.board.repository.PostRepository;
import com.son.board.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /* 게시글 추가 */
    @Transactional
    public void savePost(PostRequestDto request, int userId) {
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        request.setUser(writer);
        Post newPost = request.toPostEntity();
        postRepository.save(newPost);
        log.info("title : {}, userId : {} 등록", request.getTitle(), userId);
    }

    /* 게시글 조회 */
    // 조회 성능을 개선하기 위해 readOnly 옵션을 true로 설정
    @Transactional(readOnly = true)
    public PostResponseDto findPost(int postId) {
        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(postId + " : 게시글이 존재하지 않습니다."));

        log.info("postId : {} 조회", postId);
        return new PostResponseDto(targetPost);
    }

    /* 게시글 수정 */
    @Transactional
    public void updatePost(PostRequestDto request, int postId) {
        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(postId + " : 게시글이 존재하지 않습니다."));

        targetPost.update(request.getTitle(), request.getContent());
        log.info("postId : {} 수정", postId);
    }

    /* 게시글 삭제 */
    @Transactional
    public void deletePost(int postId) {
        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(postId + " : 게시글이 존재하지 않습니다."));

        postRepository.delete(targetPost);
        log.info("postId : {} 삭제", postId);
    }

    /* 게시물 리스트 호출 */
    // 조회 성능을 개선하기 위해 readOnly 옵션을 true로 설정
    @Transactional (readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
