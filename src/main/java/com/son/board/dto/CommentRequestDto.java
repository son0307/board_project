package com.son.board.dto;

import com.son.board.domain.Comment;
import com.son.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* 댓글 등록, 수정 처리용 dto */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String path;
    private String content;
    private int favCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;

    public Comment toCommentEntity() {
        Comment newComment = Comment.builder()
                .path(path)
                .content(content)
                .favCount(favCount)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .build();

        return newComment;
    }
}
