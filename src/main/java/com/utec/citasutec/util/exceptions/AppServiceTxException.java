package com.utec.citasutec.util.exceptions;

public class AppServiceTxException extends RuntimeException {
    public AppServiceTxException(String message) {
        super(message);
    }

    public AppServiceTxException(String message, Throwable cause) {
        super(message, cause);
    }
}