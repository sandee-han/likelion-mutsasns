package com.likelionproject.service;

import com.likelionproject.domain.Post;
import com.likelionproject.domain.dto.postdto.result.*;
import com.likelionproject.domain.dto.Response;
import com.likelionproject.domain.dto.postdto.request.PostCreateRequest;
import com.likelionproject.domain.dto.postdto.request.PostModifyRequest;
import com.likelionproject.exception.AppException;
import com.likelionproject.exception.ErrorCode;
import com.likelionproject.repository.PostRepository;
import com.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    //TODO: new 들어가는 부분 Factory 로 분리

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /*  포스트 수정  */
    public Response modifyPost(Long postId, PostModifyRequest postModifyRequest, String modifierUserName) {
        Long modifierUserId = userRepository.findByUserName(modifierUserName).get().getId();

        Post selectPost = postRepository.findById(postId)
                .filter(post -> post.getUser().getId() == modifierUserId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PERMISSION));

        selectPost.modifyPost(postModifyRequest);

        PostModifyResult postModifyResult = PostResultFactory.from(postId);
        return new Response("SUCCESS", postModifyResult);
    }
    /*     새 포스트 작성    */
    public Response createPost(PostCreateRequest postCreateRequest, Authentication authentication) {
        Post newPost = postRepository
                .save(postCreateRequest.toEntity(userRepository.findByUserName(authentication.getName()).get()));

        PostCreateResult postCreateResult = PostResultFactory.newCreateResult(newPost);
    return Response.success(postCreateResult);
    }

    @Transactional(readOnly = true)
    public Response<PageInfoResponse> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostGetResult> postAllResult = PostResultFactory.from(posts);

        PageInfoResponse pageInfoResponse = PostResultFactory.from(postAllResult);

        return new Response<PageInfoResponse>("SUCCESS", pageInfoResponse);
    }

    @Transactional(readOnly = true)
    public Response<PostGetResult> getOnePost(Long postId) {
        Post getPost = postRepository.findById(postId).orElseThrow(() -> new AppException((ErrorCode.POST_NOT_FOUND)));
        PostGetResult postGetResult = PostResultFactory.from(getPost);
        return Response.success(postGetResult);
    }

    public Response<PostDeleteResult> deletePost(Long deleteId, String deleteUserName) {
        Long deleteUserId = userRepository.findByUserName(deleteUserName).get().getId();

        Post selectedPost = postRepository.findById(deleteId)
                .filter(post -> post.getUser().getId() == deleteUserId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PERMISSION));

        postRepository.delete(selectedPost);
        PostDeleteResult postDeleteResult = PostResultFactory.newResult(deleteId);
        return new Response<PostDeleteResult>("SUCCESS", postDeleteResult);
    }
}
