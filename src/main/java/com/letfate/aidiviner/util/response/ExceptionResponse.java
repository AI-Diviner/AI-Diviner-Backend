package com.letfate.aidiviner.util.response;

import lombok.Getter;

@Getter
public class ExceptionResponse extends RuntimeException {
    private ExceptionResponseCode code;

    public ExceptionResponse(ExceptionResponseCode code, String message) {
        super(message);
        this.code = code;
    }
}