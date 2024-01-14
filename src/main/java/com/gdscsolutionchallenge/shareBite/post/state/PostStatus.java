package com.gdscsolutionchallenge.shareBite.post.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    SHARING("Sharing in progress"),
    COMPLETED("Sharing completed"),
    CANCELED("Sharing canceled");

    private final String description;

    public String getDescription() {
        return description;
    }
}
