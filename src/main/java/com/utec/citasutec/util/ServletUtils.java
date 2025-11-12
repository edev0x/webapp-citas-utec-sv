package com.utec.citasutec.util;

import jakarta.servlet.http.HttpServletResponse;

public class ServletUtils {
    public static void setJsonAsDefaultContentType(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
