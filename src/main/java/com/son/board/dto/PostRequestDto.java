package com.son.board.dto;

import com.son.board.domain.Post;
import com.son.board.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

/* 등록, 수정 처리용 dto */
@Data
public class PostRequestDto {
    int id;
    String title;
    String content;
    int viewCount;
    int favCount;
    LocalDateTime createDate;
    LocalDateTime modifiedDate;
    User user;

    public Post toPostEntity() {
        Post newPost = Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .favCount(favCount)
                .createdDate(createDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .build();

        return newPost;
    }
}
