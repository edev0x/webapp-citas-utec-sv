package com.utec.citasutec.util.security;

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
}
