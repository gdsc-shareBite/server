package com.gdscsolutionchallenge.shareBite.post.dto;

import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindPostResponseDto {
    private String title;
    private String description;
    private Integer foodQuantity;
    private LocalDateTime foodPurchaseDate;
    private LocalDateTime foodCookingDate;
    private LocalDateTime foodExpirationData;
    private LocalDateTime foodBestBeforeDate;
    private PostStatus postStatus;
    private List<String> imageUrls;
    private List<String> tags;

    @Builder
    public FindPostResponseDto(String title, String description, Integer foodQuantity,
                               LocalDateTime foodPurchaseDate, LocalDateTime foodCookingDate, LocalDateTime foodExpirationDate, LocalDateTime foodBestBeforeDate,
                               PostStatus postStatus, List<String> imageUrls, List<String> tags) {
        this.title = title;
        this.description = description;
        this.foodQuantity = foodQuantity;
        this.foodPurchaseDate = foodPurchaseDate;
        this.foodCookingDate = foodCookingDate;
        this.foodExpirationData = foodExpirationDate;
        this.foodBestBeforeDate = foodBestBeforeDate;
        this.postStatus = postStatus;
        this.imageUrls = imageUrls;
        this.tags = tags;
    }

}
