package com.utec.citasutec.util.formatters;

import com.google.gson.Gson;
import com.utec.citasutec.model.dto.response.ApiResponse;
import com.utec.citasutec.util.AttributeIdentifiers;
import com.utec.citasutec.util.ServletUtils;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Map;

@UtilityClass
public class ResponseUtils {
    private static final Gson GSON = new Gson();
    private static final String SUCCESS_RESPONSE_JSON;
    private static final String ERROR_RESPONSE_JSON;

    static {
        SUCCESS_RESPONSE_JSON = GSON.toJson(Map.of(
            AttributeIdentifiers.ERROR, false,
            AttributeIdentifiers.MESSAGE, ValidationMessages.SUCCESSFUL_OPERATION
        ));

        ERROR_RESPONSE_JSON = GSON.toJson(Map.of(
            AttributeIdentifiers.ERROR, true,
            AttributeIdentifiers.MESSAGE, ValidationMessages.UNEXPECTED_ERROR
        ));
    }

    public static void sendSuccessResponseWithContent(HttpServletResponse response, Object content) throws IOException {
        ServletUtils.setJsonAsDefaultContentType(response);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(GSON.toJson(content));
        response.getWriter().flush();
    }

    public static void sendCommonSuccess(HttpServletResponse response) throws IOException {
        ServletUtils.setJsonAsDefaultContentType(response);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(SUCCESS_RESPONSE_JSON);
        response.getWriter().flush();
    }

    public static void sendUnexpectedError(HttpServletResponse response) throws IOException {
        ServletUtils.setJsonAsDefaultContentType(response);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(ERROR_RESPONSE_JSON);
        response.getWriter().flush();
    }

    public static void sendResponse(HttpServletResponse response, ApiResponse apiResponse, int httpStatus) throws IOException {
        ServletUtils.setJsonAsDefaultContentType(response);
        response.setStatus(httpStatus);
        response.getWriter().write(GSON.toJson(apiResponse));
        response.getWriter().flush();
    }

    public static void sendNoContentResponse(HttpServletResponse response) throws IOException {
        ServletUtils.setJsonAsDefaultContentType(response);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.getWriter().flush();
    }

    public static void sendCreatedResponse(HttpServletResponse response, String location) throws IOException {
        ServletUtils.setJsonAsDefaultContentType(response);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", location);
    }
}
