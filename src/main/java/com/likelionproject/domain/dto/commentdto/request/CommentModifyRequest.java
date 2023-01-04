package com.likelionproject.domain.dto.commentdto.request;

import com.likelionproject.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentModifyRequest {
    private String comment;
}
