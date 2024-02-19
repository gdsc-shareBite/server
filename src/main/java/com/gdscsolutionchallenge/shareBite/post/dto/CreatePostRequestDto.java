package com.gdscsolutionchallenge.shareBite.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String description;
    private Integer foodQuantity;
    private String foodExpirationDate;
    private String foodManufacturingDate;
    private List<String> tags;

}
