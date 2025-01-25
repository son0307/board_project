package com.son.board.service;

import com.son.board.domain.Comment;
import com.son.board.domain.Post;
import com.son.board.domain.User;
import com.son.board.dto.CommentRequestDto;
import com.son.board.repository.CommentRepository;
import com.son.board.repository.PostRepository;
import com.son.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /* 댓글 추가 */
    @Transactional
    public void saveComment(CommentRequestDto request, int userId, int postId) {
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자 정보가 없습니다."));

        Post targetPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(postId + " : 게시글 정보가 없습니다."));

        request.setUser(writer);
        Comment newComment = request.toCommentEntity();

        targetPost.addComment(newComment);

        postRepository.save(targetPost);
        log.info("{} 댓글 추가", request.getContent());
    }

    /* 댓글 수정 */
    @Transactional
    public void updateComment(CommentRequestDto request, int commentId) {
        Comment targetComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(commentId + " : 댓글 정보가 없습니다."));

        targetComment.update(request.getContent());

        log.info("commentId : {} 수정", commentId);
    }

    /* 댓글 삭제 */
    @Transactional
    public void deleteComment(int commentId) {
        Comment targetComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(commentId + " : 댓글 정보가 없습니다."));

        targetComment.update("[삭제된 댓글입니다.]");

        log.info("commentId : {} 삭제", commentId);
    }
}
