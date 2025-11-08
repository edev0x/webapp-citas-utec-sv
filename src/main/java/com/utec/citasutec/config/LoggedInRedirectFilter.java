package com.utec.citasutec.config;

import com.utec.citasutec.util.security.RedirectUtils;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class LoggedInRedirectFilter implements Filter {

    @Inject
    private SecurityContext securityContext;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = extractPath(request);

        if (RedirectUtils.shouldRedirect(path)) {
            String redirectPath = determineRedirectPath(request);
            if (redirectPath != null) {
                response.sendRedirect(redirectPath);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    private String determineRedirectPath(HttpServletRequest request) {
        for (Map.Entry<String, String> entry : RedirectUtils.REDIRECT_MAPPING.entrySet()) {
            if (securityContext.isCallerInRole(entry.getKey())) {
                return request.getContextPath() + entry.getValue();
            }
        }
        return null;
    }
}
