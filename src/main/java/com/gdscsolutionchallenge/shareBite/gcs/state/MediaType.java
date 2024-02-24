package com.gdscsolutionchallenge.shareBite.gcs.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediaType {
    PNG("image/png"),
    JPEG("image/jpeg");

    private String mineType;
}
