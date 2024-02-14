package com.gdscsolutionchallenge.shareBite.post.service;


import com.gdscsolutionchallenge.shareBite.gcs.service.GcsService;
import com.gdscsolutionchallenge.shareBite.gcs.state.ImageType;
import com.gdscsolutionchallenge.shareBite.post.dto.CreatePostDto;
import com.gdscsolutionchallenge.shareBite.post.dto.FindAllPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.dto.FindPostResponseDto;
import com.gdscsolutionchallenge.shareBite.post.dto.UpdatePostDto;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
import com.gdscsolutionchallenge.shareBite.post.repository.PostRepository;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import com.gdscsolutionchallenge.shareBite.postTag.repository.PostTagRepository;
import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import com.gdscsolutionchallenge.shareBite.store.service.StoreService;
import com.gdscsolutionchallenge.shareBite.tag.entity.Tag;
import com.gdscsolutionchallenge.shareBite.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final GcsService gcsService;
    private final TagService tagService;
    private final StoreService storeService;

    @Transactional
    public void createPost(Long memberId, CreatePostDto createPostDto, List<MultipartFile> imageFiles) throws IOException {
        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .description(createPostDto.getDescription())
                .foodQuantity(createPostDto.getFoodQuantity())
                .foodExpirationDate(createPostDto.getFoodExpirationDate())
                .foodManufacturingDate(createPostDto.getFoodManufacturingDate())
                .build();

        Store store = storeService.findStoreByMemberId(memberId);
        post.setStore(store);

        Post savedPost = postRepository.save(post);

        // tag 설정
        for(String tagName : createPostDto.getTags()) {
            Tag tag = tagService.verifyTag(tagName);

            PostTag postTag = new PostTag();
            postTag.setTag(tag);
            postTag.setPost(savedPost);
            postTagRepository.save(postTag);
        }

        // image upload
        gcsService.uploadImages(ImageType.POST_IMAGE, imageFiles, String.valueOf(savedPost.getPostId()));
    }

    @Transactional
    public void updatePost(Long postId, UpdatePostDto updatePostDto, List<MultipartFile> imageFiles) throws IOException {
        Post post = verifyPost(postRepository.findById(postId));

        postTagRepository.deleteByPost_PostId(postId);

        for(String tagName : updatePostDto.getTags()) {
            Tag tag = tagService.verifyTag(tagName);

            PostTag postTag = new PostTag();
            postTag.setTag(tag);
            postTag.setPost(post);
            postTagRepository.save(postTag);
        }

        post.update(updatePostDto.getTitle(),
                updatePostDto.getDescription(),
                updatePostDto.getFoodQuantity(),
                updatePostDto.getFoodExpirationDate(),
                updatePostDto.getFoodManufacturingDate(),
                updatePostDto.getPostStatus());

        gcsService.updateImages(ImageType.POST_IMAGE, imageFiles, String.valueOf(post.getPostId()));
    }

    public FindPostResponseDto findPost(Long postId) {
        Post post = verifyPost(postRepository.findById(postId));

        List<String> tags = post.getPostTags().stream().map(postTag ->
            postTag.getTag().getName()).toList();

        List<String> imageUrls = gcsService.findImages(ImageType.POST_IMAGE, String.valueOf(postId));

        return FindPostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .description(post.getDescription())
                .foodQuantity(post.getFoodQuantity())
                .foodExpirationDate(post.getFoodExpirationDate())
                .foodManufacturingDate(post.getFoodManufacturingDate())
                .postStatus(post.getPostStatus())
                .imageUrls(imageUrls)
                .tags(tags)
                .build();

    }

    public FindAllPostResponseDto findAllPost(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        List<FindPostResponseDto> findPostResponseDtoList = postPage.getContent().stream()
                .map(post -> {
                    Long postId = post.getPostId();

                    List<String> tags = post.getPostTags().stream().map(postTag ->
                            postTag.getTag().getName()).toList();

                    List<String> imageUrls = gcsService.findImages(ImageType.POST_IMAGE, String.valueOf(postId));

                    return FindPostResponseDto.builder()
                            .postId(postId)
                            .title(post.getTitle())
                            .description(post.getDescription())
                            .foodQuantity(post.getFoodQuantity())
                            .foodExpirationDate(post.getFoodExpirationDate())
                            .foodManufacturingDate(post.getFoodManufacturingDate())
                            .postStatus(post.getPostStatus())
                            .imageUrls(imageUrls)
                            .tags(tags)
                            .build();
                })
                .collect(Collectors.toList());

        return new FindAllPostResponseDto(findPostResponseDtoList,
                postPage.getTotalPages(),
                postPage.getTotalElements());
    }

//    public void deletePost(Long postId) {
//        Post post = verifyPost(postRepository.findById(postId));
//
//        postRepository.delete(post);
//    }

    private Post verifyPost(Optional<Post> optionalPost) {
        return optionalPost.orElseThrow(() -> new RuntimeException());
    }
}
