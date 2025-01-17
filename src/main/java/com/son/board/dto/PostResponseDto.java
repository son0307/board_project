package com.son.board.dto;

import com.son.board.domain.Comment;
import com.son.board.domain.Post;
import com.son.board.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/* 게시글 조회 처리용 dto */
@Getter
public class PostResponseDto {
    private final int id;
    private final String title;
    private final String content;
    private final int viewCount;
    private final int favCount;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final User user;
    private final List<Comment> comments;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.favCount = post.getFavCount();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.user = post.getUser();
        this.comments = post.getComments();
    }
}
