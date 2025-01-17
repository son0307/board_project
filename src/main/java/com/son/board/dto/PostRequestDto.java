package com.son.board.dto;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* 게시글 등록, 수정 처리용 dto */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private int viewCount;
    private int favCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;

    public Post toPostEntity() {
        Post newPost = Post.builder()
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .favCount(favCount)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .build();

        return newPost;
    }
}
