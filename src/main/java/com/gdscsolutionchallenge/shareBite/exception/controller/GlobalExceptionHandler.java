package com.gdscsolutionchallenge.shareBite.exception.controller;

import com.gdscsolutionchallenge.shareBite.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(FileOperationException.class)
    public ResponseEntity<String> handleUploadFailedException(FileOperationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<String> handleUnsupportedMediaTypeException(UnsupportedMediaTypeException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("Bad request: " + e.getMessage());
//    }
//
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body("Unauthorized: " + e.getMessage());
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    public ResponseEntity<String> handleForbiddenException(ForbiddenException e) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body("Forbidden: " + e.getMessage());
//    }
//
//    @ExceptionHandler(MethodNotAllowedException.class)
//    public ResponseEntity<String> handleMethodNotAllowedException(MethodNotAllowedException e) {
//        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
//                .body("Method not allowed: " + e.getMessage());
//    }
//
//    @ExceptionHandler(InternalServerException.class)
//    public ResponseEntity<String> handleInternalServerException(InternalServerException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Internal server error: " + e.getMessage());
//    }
}
