package com.gdscsolutionchallenge.shareBite.post.dto;

import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllPostResponseDto {
    List<FindPostResponseDto> findPostResponseDtoList;

    public static class FindPostResponseDto {
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
    }
}