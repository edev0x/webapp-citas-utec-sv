package com.utec.citasutec.model.dto.response;


import lombok.Builder;

import java.util.Map;

@Builder
public final class ApiResponse {
    private final String message;
    private final boolean error;
    private final Map<String, Object> data;
    private final Map<String, String> validationErrors;

    private ApiResponse(String message, boolean error, Map<String, Object> data, Map<String, String> validationErrors) {
        this.message = message;
        this.error = error;
        this.data = data;
        this.validationErrors = validationErrors;
    }

    public static ApiResponse success(String message) {
        return ApiResponse.builder().message(message).error(false).build();
    }

    public static ApiResponse success(String message, Map<String, Object> data) {
        return ApiResponse.builder().message(message).error(false).data(data).build();
    }

    public static ApiResponse error(String message) {
        return ApiResponse.builder().message(message).error(true).build();
    }

    public static ApiResponse validationError(Map<String, String> validationErrors) {
        return ApiResponse.builder().validationErrors(validationErrors).build();
    }
}
