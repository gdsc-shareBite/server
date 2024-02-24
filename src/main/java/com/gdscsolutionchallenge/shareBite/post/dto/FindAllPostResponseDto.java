package com.gdscsolutionchallenge.shareBite.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllPostResponseDto {
    private List<FindPostResponseDto> posts;
    private int totalPages;
    private long totalElements;

}