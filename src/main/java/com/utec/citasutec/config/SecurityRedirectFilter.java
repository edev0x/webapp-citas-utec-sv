package com.utec.citasutec.config;

import java.io.IOException;
import java.util.Objects;

import com.utec.citasutec.util.security.RedirectUtils;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter("/*")
public class SecurityRedirectFilter implements Filter {

    @Inject
    private SecurityContext securityContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        boolean isPublic = RedirectUtils.isPublicPath(path);
        boolean isLoggedIn = Objects.nonNull(securityContext.getCallerPrincipal());


        if (isPublic && isLoggedIn) {
            String redirectPath = RedirectUtils.determineRedirectPath(httpRequest, securityContext);
            if (redirectPath != null) {
                httpResponse.sendRedirect(redirectPath);
                return;
            }
        }

        chain.doFilter(request, response);
    }

}
