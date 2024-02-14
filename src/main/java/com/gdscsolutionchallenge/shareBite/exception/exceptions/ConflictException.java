package com.gdscsolutionchallenge.shareBite.exception.exceptions;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;

public class ConflictException extends RuntimeException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public ConflictException(ErrorCode errorCode, Object value) {
        super(errorCode.getMessage() + ": " + value.toString() + " is conflict.");
    }
}

