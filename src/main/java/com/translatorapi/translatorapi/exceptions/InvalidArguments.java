package com.translatorapi.translatorapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason="Wrong input.")
public class InvalidArguments extends Exception {
    public InvalidArguments() {
        super();
    }

    public InvalidArguments(String message) {
        super(message);
    }

    public InvalidArguments(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArguments(Throwable cause) {
        super(cause);
    }
}
