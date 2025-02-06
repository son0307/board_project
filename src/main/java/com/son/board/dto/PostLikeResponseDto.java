package com.son.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeResponseDto {
    private boolean liked;
    private int favCount;
}
