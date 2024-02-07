package com.gdscsolutionchallenge.shareBite.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokensDto {
    private String accessToken;
    private String refreshToken;
}
