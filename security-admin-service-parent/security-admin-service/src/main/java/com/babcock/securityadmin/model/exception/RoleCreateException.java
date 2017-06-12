package com.babcock.securityadmin.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RoleCreateException extends RuntimeException {

    public RoleCreateException(String message) {
        super(message);
    }

    public RoleCreateException(Throwable throwable) {
        super(throwable);
    }
}
