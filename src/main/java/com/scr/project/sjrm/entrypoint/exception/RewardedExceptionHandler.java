package com.scr.project.sjrm.entrypoint.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RewardedExceptionHandler {

    @ExceptionHandler(OnRewardedNotFound.class)
    public ResponseEntity<String> handleRewardedNotFound(OnRewardedNotFound e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
}
