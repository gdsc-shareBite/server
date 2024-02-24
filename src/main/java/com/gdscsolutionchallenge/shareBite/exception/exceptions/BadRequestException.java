package com.gdscsolutionchallenge.shareBite.exception.exceptions;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;

public class BadRequestException extends RuntimeException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + ": " + message);
    }
}
