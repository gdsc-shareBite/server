package com.gdscsolutionchallenge.shareBite.post.dto;

import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindPostResponseDto {
    private Long postId;
    private String title;
    private String description;
    private Integer foodQuantity;
    private LocalDateTime foodExpirationData;
    private LocalDateTime foodManufacturingDate;
    private PostStatus postStatus;
    private List<String> imageUrls;
    private List<String> tags;

    @Builder
    public FindPostResponseDto(Long postId, String title, String description, Integer foodQuantity,
                               LocalDateTime foodExpirationDate, LocalDateTime foodManufacturingDate,
                               PostStatus postStatus, List<String> imageUrls, List<String> tags) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.foodQuantity = foodQuantity;
        this.foodExpirationData = foodExpirationDate;
        this.foodManufacturingDate = foodManufacturingDate;
        this.postStatus = postStatus;
        this.imageUrls = imageUrls;
        this.tags = tags;
    }

}
