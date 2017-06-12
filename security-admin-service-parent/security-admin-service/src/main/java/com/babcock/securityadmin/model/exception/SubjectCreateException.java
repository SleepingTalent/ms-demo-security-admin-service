package com.babcock.securityadmin.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubjectCreateException extends RuntimeException {

    public SubjectCreateException(String message) {
        super(message);
    }

    public SubjectCreateException(Throwable throwable) {
        super(throwable);
    }
}
