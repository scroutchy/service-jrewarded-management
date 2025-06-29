package com.scr.project.sjrm.entrypoint.exception;

import com.scr.project.sjrm.entrypoint.exception.RewardedExceptions.OnRewardedNotFound;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.scr.project.sjrm.entrypoint.exception.RewardedError.REWARDED_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RewardedExceptionHandler {

    @ExceptionHandler(OnRewardedNotFound.class)
    public ResponseEntity<ErrorResponse> handleRewardedNotFound(OnRewardedNotFound e) {
        var body = new ErrorResponse(REWARDED_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(body);
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {

        private RewardedError errorCode;

        private String message;
    }
}
