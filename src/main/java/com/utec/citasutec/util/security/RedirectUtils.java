package com.utec.citasutec.util.security;

import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;

public class RedirectUtils {
    private static final Set<String> PUBLIC_PAGES = Set.of(
        "/login.jsp",
        "/index.jsp",
        "/signup",
        "/login",
        "/"
    );

    public static final Map<String, String> REDIRECT_MAPPING = Map.of(
        "ADMIN", "/app/dashboard",
        "PROFESIONAL", "/app/p/home",
        "ESTUDIANTE", "/app/s/home"
    );

    public static boolean shouldRedirect(String path) {
        return PUBLIC_PAGES.contains(path);
    }

    public static String determineRedirectPath(HttpServletRequest request, SecurityContext securityContext) {
        for (Map.Entry<String, String> entry : RedirectUtils.REDIRECT_MAPPING.entrySet()) {
            if (securityContext.isCallerInRole(entry.getKey())) {
                return request.getContextPath() + entry.getValue();
            }
        }
        return null;
    }
}
