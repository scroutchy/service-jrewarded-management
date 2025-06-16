package com.scr.project.sjrm.entrypoint.exception;

public class OnRewardedNotFound extends RuntimeException {

    public OnRewardedNotFound(Long id) {
        super("Rewarded with id " + id + " not found");
    }
}
