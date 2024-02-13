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
public class UpdatePostDto {
    private String title;
    private String description;
    private Integer foodQuantity;
    private LocalDateTime foodExpirationDate;
    private LocalDateTime foodManufacturingDate;
    private PostStatus postStatus;
    private List<Long> tagIdS;

}
