package com.gdscsolutionchallenge.shareBite.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String title;
    private String description;
    private Integer foodQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime foodExpirationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime foodManufacturingDate;
    private List<String> tags;

    @Builder
    public CreatePostDto(String title, String description, Integer foodQuantity, String foodExpirationDate, String foodManufacturingDate, List<String> tags) {
        this.title = title;
        this.description = description;
        this.foodQuantity = foodQuantity;
        this.foodExpirationDate = LocalDate.parse(foodExpirationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        this.foodManufacturingDate = LocalDate.parse(foodManufacturingDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        this.tags = tags;
    }

}
