package com.son.board.dto;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* 등록, 수정 처리용 dto */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    String title;
    String content;
    int viewCount;
    int favCount;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    User user;

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
