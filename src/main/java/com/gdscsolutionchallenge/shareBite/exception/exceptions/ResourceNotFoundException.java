package com.gdscsolutionchallenge.shareBite.exception.exceptions;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
