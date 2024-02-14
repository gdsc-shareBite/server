package com.gdscsolutionchallenge.shareBite.exception.exceptions;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;

public class FileOperationException extends RuntimeException {
    public FileOperationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public FileOperationException(ErrorCode errorCode, Object value) {
        super(errorCode.getMessage() + ": " + value.toString());
    }
}
