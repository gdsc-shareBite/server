package com.gdscsolutionchallenge.shareBite.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {
    private String title;
    private String description;
    private Integer foodQuantity;
    private LocalDateTime foodPurchaseDate;
    private LocalDateTime foodCookingDate;
    private LocalDateTime foodExpirationDate;
    private LocalDateTime foodBestBeforeDate;
    private MultipartFile[] imageFiles;
    private List<String> tags;

}
