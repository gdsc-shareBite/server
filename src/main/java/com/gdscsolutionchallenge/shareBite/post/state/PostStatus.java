package com.gdscsolutionchallenge.shareBite.post.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
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

    @JsonCreator
    public static PostStatus findPostStatus(String targetStatus) {
        for(PostStatus status : values()) {
            if(status.name().equals(targetStatus)) return status;
        }

        throw new ResourceNotFoundException(ErrorCode.NOT_FOUND_POST_STATUS);
    }
}
