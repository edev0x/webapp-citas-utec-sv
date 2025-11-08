package com.utec.citasutec.util.exceptions;

import java.io.Serial;

import lombok.Getter;

@Getter
public class RepositoryTransactionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public RepositoryTransactionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public RepositoryTransactionException(String message) {
        super(message);
        this.errorCode = "UNKNOWN_ERROR";
    }
    
}
