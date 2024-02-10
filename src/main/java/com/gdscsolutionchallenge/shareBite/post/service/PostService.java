package com.gdscsolutionchallenge.shareBite.post.service;


import com.gdscsolutionchallenge.shareBite.gcs.service.GcsService;
import com.gdscsolutionchallenge.shareBite.post.dto.CreatePostDto;
import com.gdscsolutionchallenge.shareBite.post.dto.FindPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.dto.FindAllPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.dto.UpdatePostDto;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
import com.gdscsolutionchallenge.shareBite.post.repository.PostRepository;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import com.gdscsolutionchallenge.shareBite.tag.entity.Tag;
import com.gdscsolutionchallenge.shareBite.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final GcsService gcsService;

    @Transactional
    public void createPost(CreatePostDto createPostDto) throws IOException {
        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .description(createPostDto.getDescription())
                .foodQuantity(createPostDto.getFoodQuantity())
                .foodPurchaseDate(createPostDto.getFoodPurchaseDate())
                .foodExpirationDate(createPostDto.getFoodExpirationDate())
                .foodBestBeforeDate(createPostDto.getFoodBestBeforeDate())
                .build();

        post.setStore();

        Post savedPost = postRepository.save(post);

        // tag 설정
        for(String tag : createPostDto.getTags()) {
            Optional<Tag> optionalTag = tagRepository.findByName(tag);
            if(optionalTag.isEmpty()) {
                // todo: 존재하지 않는 tag 예외
            }

            PostTag postTag = new PostTag();
            postTag.setTag(optionalTag.get());
            postTag.setPost(post);

            post.getPostTags().add(postTag);
        }

        // image upload
        gcsService.updateImages(createPostDto.getImageFiles(), savedPost.getPostId());
    }

    public void updatePost(Long postId, UpdatePostDto updatePostDto) {
        Post post = verifyPost(postRepository.findById(postId));

        post.update();

    }

    public FindPostResponseDto findPost(Long postId) {
        Post post = verifyPost(postRepository.findById(postId));

        List<String> tags = post.getPostTags().stream().map(postTag ->
            postTag.getTag().getName()).toList();

        return FindPostResponseDto.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .foodQuantity(post.getFoodQuantity())
                .foodCookingDate(post.getFoodCookingDate())
                .foodExpirationDate(post.getFoodExpirationDate())
                .foodBestBeforeDate(post.getFoodBestBeforeDate())
                .postStatus(post.getPostStatus())
                .imageUrls(gcsService.findImages(postId))
                .tags(tags)
                .build();

    }

    public FindAllPostResponseDto findAllPost(Pageable pageable) {
        Page<Post> pages = postRepository.findByAllByOrderModifiedAtDesc(pageable);

    }

    public void deletePost(Long postId) {
        Post post = verifyPost(postRepository.findById(postId));

        postRepository.delete(post);
    }

    private Post verifyPost(Optional<Post> optionalPost) {
        return optionalPost.orElseThrow(() -> new RuntimeException());
    }
}
