package com.scr.project.sjrm;

import org.awaitility.Awaitility;

import java.util.concurrent.TimeUnit;

public class TestExtensions {

    private TestExtensions() {
    }

    public static void awaitUntil(long seconds, Runnable assertion) {
        Awaitility.await().atMost(seconds, TimeUnit.SECONDS).untilAsserted(assertion::run);
    }

    public static void awaitUntil(Runnable assertion) {
        awaitUntil(30, assertion);
    }

}
