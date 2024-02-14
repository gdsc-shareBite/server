package com.gdscsolutionchallenge.shareBite.exception.exceptions;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;

public class UnsupportedMediaTypeException extends RuntimeException {
    public UnsupportedMediaTypeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public UnsupportedMediaTypeException(ErrorCode errorCode, Object value) {
        super(errorCode.getMessage() + ": " + value.toString() + " is unsupported.");
    }
}
