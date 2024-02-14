package com.gdscsolutionchallenge.shareBite.exception.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Not found error
    NOT_FOUND_MEMBER("Not found member "),
    NOT_FOUND_ROLE("Not found role"),
    NOT_FOUND_STORE("Not found store"),
    NOT_FOUND_TAG("Not found tag"),
    NOT_FOUND_POST_STATUS("Not found post status"),

    // Conflict error
    CONFLICT_MEMBER("Conflict member"),
    CONFLICT_TAG("Conflict tag"),

    // File operation error
    FILE_OPERATION_FAILED("File operation failed"),

    // Unsupported media error
    UNSUPPORTED_MEDIA_TYPE("Unsupported media type"),

    // Auth error
    BAD_REQUEST("Bad request");

    private final String message;
    ErrorCode(String message) {
        this.message = message;
    }

}
