package com.likelionproject.controller;

import com.likelionproject.domain.dto.postdto.PostCreateRequest;
import com.likelionproject.domain.dto.postdto.PostResponse;
import com.likelionproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> newPost(@RequestBody PostCreateRequest request, Authentication authentication) {
        return ResponseEntity.ok().body(postService.createPost(request, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getOnePost(@PathVariable Long id) {
        PostResponse postResponse = postService.getOnePost(id);
        return ResponseEntity.ok().body(postResponse);
    }
}
