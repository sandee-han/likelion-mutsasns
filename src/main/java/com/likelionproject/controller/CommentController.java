package com.likelionproject.controller;

import com.likelionproject.domain.dto.Response;
import com.likelionproject.domain.dto.commentdto.request.CommentCreateRequest;
import com.likelionproject.domain.dto.commentdto.request.CommentModifyRequest;
import com.likelionproject.domain.dto.commentdto.result.CommentCreateResult;
import com.likelionproject.domain.dto.commentdto.result.CommentDeleteResult;
import com.likelionproject.domain.dto.commentdto.result.CommentModifyResult;
import com.likelionproject.domain.dto.commentdto.result.CommentPageResult;
import com.likelionproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Response<CommentCreateResult>> createComment(@PathVariable("postId") Long postId, @RequestBody CommentCreateRequest commentCreateRequest, Authentication authentication) {
        Response<CommentCreateResult> response = commentService.createComment(postId, commentCreateRequest, authentication);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Response<CommentModifyResult>> modifyComment(@PathVariable("commentId") Long commentId, @RequestBody CommentModifyRequest commentModifyRequest, Authentication authentication) {
        Response<CommentModifyResult> response = commentService.modifyComment(commentId, commentModifyRequest, authentication);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Response<CommentDeleteResult>> deleteComment(@PathVariable("commentId") Long commentId, Authentication authentication) {
        Response<CommentDeleteResult> response = commentService.deleteComment(commentId, authentication);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Response<CommentPageResult>> getComments(@PathVariable("postId") Long postId, Pageable pageable) {
        Response<CommentPageResult> response = commentService.getComments(postId, pageable);
        return ResponseEntity.ok().body(response);
    }

}
