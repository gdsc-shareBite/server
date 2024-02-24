package com.gdscsolutionchallenge.shareBite.post.controller;

import com.gdscsolutionchallenge.shareBite.post.dto.*;
import com.gdscsolutionchallenge.shareBite.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal UserDetails userDetails,
                                        @Valid @RequestPart(value = "createPostRequestDto") CreatePostRequestDto createPostRequestDto,
                                        @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {
        Long memberId = Long.parseLong(userDetails.getUsername());

        CreatePostDto createPostDto = CreatePostDto.builder()
                .title(createPostRequestDto.getTitle())
                .description(createPostRequestDto.getDescription())
                .foodQuantity(createPostRequestDto.getFoodQuantity())
                .foodExpirationDate(createPostRequestDto.getFoodExpirationDate())
                .foodManufacturingDate(createPostRequestDto.getFoodManufacturingDate())
                .tags(createPostRequestDto.getTags())
                .build();

        postService.createPost(memberId, createPostDto, imageFiles);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,
                                        @Valid @RequestPart(value = "updatePostRequestDto") UpdatePostRequestDto updatePostRequestDto,
                                        @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {
        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .title(updatePostRequestDto.getTitle())
                .description(updatePostRequestDto.getDescription())
                .foodQuantity(updatePostRequestDto.getFoodQuantity())
                .foodExpirationDate(updatePostRequestDto.getFoodExpirationDate())
                .foodManufacturingDate(updatePostRequestDto.getFoodManufacturingDate())
                .tags(updatePostRequestDto.getTags())
                .postStatus(updatePostRequestDto.getPostStatus())
                .build();

        postService.updatePost(postId, updatePostDto, imageFiles);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponseDto> findPost(@PathVariable Long postId) {

        return new ResponseEntity<>(postService.findPost(postId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FindAllPostResponseDto> findAllPost(@RequestParam(defaultValue = "0") int pageStartIdx,
                                                              @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageStartIdx, pageSize);
        return new ResponseEntity<>(postService.findAllPost(pageable), HttpStatus.OK);
    }
//
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
//        postService.deletePost(postId);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
