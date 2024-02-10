package com.gdscsolutionchallenge.shareBite.post.controller;

import com.gdscsolutionchallenge.shareBite.post.dto.CreatePostDto;
import com.gdscsolutionchallenge.shareBite.post.dto.FindPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.dto.FindAllPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.dto.UpdatePostDto;
import com.gdscsolutionchallenge.shareBite.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(CreatePostDto createPostDto) throws IOException {
        postService.createPost(createPostDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,
                                        @RequestBody UpdatePostDto updatePostDto) {
        postService.updatePost(postId, updatePostDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponseDto> findPost(@PathVariable Long postId) {

        return new ResponseEntity<>(postService.findPost(postId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FindAllPostResponseDto> findAllPost(Pageable pageable) {

        return new ResponseEntity<>(postService.findAllPost(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
