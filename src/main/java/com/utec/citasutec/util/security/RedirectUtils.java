package com.utec.citasutec.util.security;

import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;

import com.utec.citasutec.util.ResourceConstants;

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
            "ESTUDIANTE", "/app/s/home",
            "AUDITOR", "/app/a/home"
    );

    private static final Map<String, String> RESOURCE_PATHS = Map.of(
            ResourceConstants.USERS, "/WEB-INF/views/protected/admin/manage/users.jsp",
            ResourceConstants.PROFESSIONALS, "/WEB-INF/views/protected/admin/manage/professionals.jsp",
            ResourceConstants.STUDENTS, "/WEB-INF/views/protected/admin/manage/students.jsp",
            ResourceConstants.APPOINTMENTS, "/WEB-INF/views/protected/admin/manage/appointments.jsp",
            ResourceConstants.AUDITS, "/WEB-INF/views/protected/admin/manage/audits.jsp",
            ResourceConstants.ROLES, "/WEB-INF/views/protected/admin/manage/roles.jsp"
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

    public static boolean isPublicPath(String path) {
        return PUBLIC_PAGES.contains(path);
    }

    public static String getResourcePath(String resource) {
        return RESOURCE_PATHS.get(resource);
    }
}
