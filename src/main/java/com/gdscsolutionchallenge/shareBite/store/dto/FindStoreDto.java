package com.gdscsolutionchallenge.shareBite.store.dto;

import com.gdscsolutionchallenge.shareBite.store.state.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindStoreDto {
    private String name;
    private String address;
    private Integer likeCount;
    private Integer dislikeCount;
    private List<String> storeImageUrls;
    private RegistrationStatus registrationStatus;
}
