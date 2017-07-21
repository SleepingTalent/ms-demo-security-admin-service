package com.babcock.test.helper.asserter;

public class WaitUntilAssertionError extends RuntimeException {

    public WaitUntilAssertionError(final String message) {
        super(message);
    }
}
