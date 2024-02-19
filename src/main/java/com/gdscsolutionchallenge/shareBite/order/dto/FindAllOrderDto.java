package com.gdscsolutionchallenge.shareBite.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllOrderDto {
    private List<FindOrderDto> orders;
    private int totalPages;
    private long totalElements;

}
